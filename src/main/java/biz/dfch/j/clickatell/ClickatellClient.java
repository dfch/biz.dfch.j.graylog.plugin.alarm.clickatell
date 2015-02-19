package biz.dfch.j.clickatell;

import biz.dfch.j.clickatell.rest.accountbalance.AccountBalanceResponse;
import biz.dfch.j.clickatell.rest.coverage.CoverageResponse;
import biz.dfch.j.clickatell.rest.message.MessageResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.msgpack.annotation.NotNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ClickatellClient
{
    private static final Header headerApiVersion = new Header()
    {
        @Override
        public String getName()
        {
            return "X-Version";
        }

        @Override
        public String getValue()
        {
            return "1";
        }

        @Override
        public HeaderElement[] getElements() throws ParseException
        {
            return new HeaderElement[0];
        }
    };
    private static final Header headerContentType = new Header()
    {
        @Override
        public String getName()
        {
            return "Content-Type";
        }

        @Override
        public String getValue()
        {
            return "application/json";
        }

        @Override
        public HeaderElement[] getElements() throws ParseException
        {
            return new HeaderElement[0];
        }
    };
    private static final Header headerAccept = new Header()
    {
        @Override
        public String getName()
        {
            return "Accept";
        }

        @Override
        public String getValue()
        {
            return "application/json";
        }

        @Override
        public HeaderElement[] getElements() throws ParseException
        {
            return new HeaderElement[0];
        }
    };
    private String authToken = "";
    private String bearerToken = "";
    private static final String APICOVERAGE = "https://api.clickatell.com/rest/coverage/%s";
    private static final String APIMESSAGE = "https://api.clickatell.com/rest/message";
    private static final String APIBALANCE = "https://api.clickatell.com/rest/account/balance";

    private URI uriCoverage = null;
    private URI uriMessage = null;
    private URI uriBalance = null;

    private static final Logger LOG = LoggerFactory.getLogger(ClickatellClient.class);

    public ClickatellClient(@NotNullable String authToken)
            throws URISyntaxException, NullPointerException
    {
        if(null == authToken || authToken.isEmpty())
        {
            throw new NullPointerException("authToken: Parameter validation FAILED. Parameter cannot be null or empty.");
        }
        this.authToken = authToken;
        this.bearerToken = String.format("Bearer %s", authToken);
        uriMessage = new URI(APIMESSAGE);
        uriBalance = new URI(APIBALANCE);
    }

    public AccountBalanceResponse getBalance()
            throws URISyntaxException, ClientProtocolException, IOException
    {
        String response = Request.Get(uriBalance.toString())
                .setHeader(headerApiVersion)
                .setHeader(headerContentType)
                .setHeader(headerAccept)
                .setHeader("Authorization", bearerToken)
                .execute()
                .returnContent()
                .asString();

        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        AccountBalanceResponse accountBalanceResponse = om.readValue(response, AccountBalanceResponse.class);
        return accountBalanceResponse;
    }
    public CoverageResponse getCoverage(@NotNullable String recipient)
            throws URISyntaxException, IOException
    {
        URI uriCoverage = new URI(String.format(APICOVERAGE, recipient));
        String response = Request.Get(uriCoverage.toString())
                .setHeader(headerApiVersion)
                .setHeader(headerContentType)
                .setHeader(headerAccept)
                .setHeader("Authorization", bearerToken)
                .execute()
                .returnContent()
                .asString();

        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        CoverageResponse coverageResponse = om.readValue(response, CoverageResponse.class);
        return coverageResponse;
    }

    public MessageResponse sendMessage(String recipient, String message)
            throws IOException
    {
        return sendMessage(recipient, message, 0, 0);
    }

    public MessageResponse sendMessage(String recipient, String message, int maxCredits, int maxParts)
            throws IOException, HttpResponseException
    {
        class ClickatellMessage
        {
            public ArrayList<String> to = new ArrayList<String>();
            public String text;
            public String maxCredits;
            public String maxMessageParts;
        }
        try
        {
            ClickatellMessage clickatellMessage = new ClickatellMessage();
            clickatellMessage.to.add(recipient);
            clickatellMessage.text = message;
            clickatellMessage.maxCredits = (0 == maxCredits) ? null : Integer.toString(maxCredits);
            clickatellMessage.maxMessageParts = (0 == maxParts) ? null : Integer.toString(maxParts);

            ObjectMapper om = new ObjectMapper();
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String request = om.writeValueAsString(clickatellMessage);
            String response = Request.Post(uriMessage.toString())
                    .setHeader(headerApiVersion)
                    .setHeader(headerContentType)
                    .setHeader(headerAccept)
                    .setHeader("Authorization", bearerToken)
                    .bodyString(request, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();
            MessageResponse messageResponse = om.readValue(response, MessageResponse.class);
            return messageResponse;
        }
        catch (HttpResponseException ex)
        {
            int statusCode = ex.getStatusCode();
            switch(statusCode)
            {
                case 410:
                    LOG.error(String.format("Sending message to '%s' FAILED with HTTP %d. No coverage of recipient.", recipient, statusCode));
                    break;
                default:
                    break;
            }
            throw ex;
        }
    }
    public List<MessageResponse> sendMessage(@NotNullable List<String> recipients, String message)
            throws IOException
    {
        return sendMessage(recipients, message, 0, 0);
    }
    public List<MessageResponse> sendMessage(@NotNullable List<String> recipients, String message, int maxCredits, int maxParts)
            throws IOException
    {
        List<MessageResponse> messageResponses = new ArrayList<MessageResponse>();
        for(String recipient : recipients)
        {
            try
            {
                MessageResponse messageResponse = sendMessage(recipient, message, maxCredits, maxParts);
                messageResponses.add(messageResponse);
            }
            catch(Exception ex)
            {
                LOG.error(String.format("Sending message to '%s' FAILED. %s", recipient, ex.getMessage()));
                continue;
            }
        }
        return messageResponses;
    }
}

/**
 *
 *
 * Copyright 2015 Ronald Rink, d-fens GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
