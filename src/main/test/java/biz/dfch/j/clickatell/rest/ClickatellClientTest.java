package biz.dfch.j.clickatell.rest;

import biz.dfch.j.clickatell.ClickatellClient;
import biz.dfch.j.clickatell.rest.accountbalance.AccountBalanceResponse;
import biz.dfch.j.clickatell.rest.coverage.CoverageResponse;
import biz.dfch.j.clickatell.rest.message.Message;
import biz.dfch.j.clickatell.rest.message.MessageResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClickatellClientTest
{
    private String authToken = "";

    @BeforeClass
    public static void BeforeClass()
    {
        System.out.println("BeforeClass");
    }
    @Before
    public void Before()
    {
        System.out.println("Before");
        authToken = "CLICKATELL-REST-API-KEY-AUTH-TOKEN";
    }

    @Test
    public void A1000_doNothingReturnsTrue()
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        assertEquals(true, true);

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test
    public void A1100_doCreateClientReturnsTrue()
            throws URISyntaxException, ClientProtocolException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient(authToken);

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test(expected = NullPointerException.class)
    public void A1110_doCreateClientNullTokenThrowsNullPointerException()
            throws URISyntaxException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient(null);

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test(expected = NullPointerException.class)
    public void A1115_doCreateClientEmptyTokenThrowsNullPointerException()
            throws URISyntaxException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient("");

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test
    public void A1120_doGetBalanceReturnsTrue()
            throws URISyntaxException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient(authToken);
        AccountBalanceResponse accountBalanceResponse = clickatellClient.getBalance();
        Double balance = Double.parseDouble(accountBalanceResponse.getData().getBalance());
        System.out.println(String.format("Balance is %f", balance));
        assertEquals(true, balance > 0);

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test
    public void A1130_doGetBalanceGreater1000000ReturnsFalse()
            throws URISyntaxException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient(authToken);
        AccountBalanceResponse accountBalanceResponse = clickatellClient.getBalance();
        Double balance = Double.parseDouble(accountBalanceResponse.getData().getBalance());
        assertEquals(false, balance > 1000000);

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test
    public void A1140_doGetCoverageReturnsTrue()
            throws URISyntaxException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient(authToken);
        CoverageResponse coverageResponse = clickatellClient.getCoverage("27999112345");
        assertEquals(true, coverageResponse.getData().isRoutable());

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test
    public void A1150_doGetCoverageReturnsFalse()
            throws URISyntaxException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient(authToken);
        CoverageResponse coverageResponse = clickatellClient.getCoverage("112");
        assertEquals(false, coverageResponse.getData().isRoutable());

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test
    public void A1160_doGetCoverageInvalidNumberReturnsFalse()
            throws URISyntaxException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient(authToken);
        CoverageResponse coverageResponse = clickatellClient.getCoverage("INVALID_NUMBER");
        assertEquals(false, coverageResponse.getData().isRoutable());

        System.out.println(String.format("%s: RET.", fn));
    }

    @Test
    public void A1170_doSendMessageReturnsTrue()
            throws URISyntaxException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        ClickatellClient clickatellClient = new ClickatellClient(authToken);
        MessageResponse messageResponse = clickatellClient.sendMessage("27999112345", "tralala");
        List<Message> messages = messageResponse.getData().getMessage();
        for(Message message : messages)
        {
            System.out.println(String.format("%s; %s; %s", message.getTo(), message.getApiMessageId(), message.isAccepted()));
            assertEquals(true, message.isAccepted());
        }
        System.out.println(String.format("%s: RET.", fn));
    }

    @Test
    public void A1175_doSendMessageMaxPartsExceededThrowsHttpResponseException400()
            throws URISyntaxException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));

        try
        {
            ClickatellClient clickatellClient = new ClickatellClient(authToken);
            String messageText = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
            MessageResponse messageResponse = clickatellClient.sendMessage("27999112345", messageText, 0, 1);
            assertEquals(null, messageResponse);
        }
        catch(HttpResponseException ex)
        {
            int statusCode = ex.getStatusCode();
            assertEquals(400, statusCode);
        }
        finally
        {
            System.out.println(String.format("%s: RET.", fn));
        }
    }

    @Test
    public void A1180_doSendMessageNoCoverageThrowsHttpResponseException410()
            throws URISyntaxException, IOException
    {
        String fn = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(String.format("%s: CALL.", fn));
        try
        {
            ClickatellClient clickatellClient = new ClickatellClient(authToken);
            String messageText = "testmessage";
            MessageResponse messageResponse = clickatellClient.sendMessage("112", messageText, 0, 0);
            assertEquals(null, messageResponse);
        }
        catch(HttpResponseException ex)
        {
            int statusCode = ex.getStatusCode();
            assertEquals(410, statusCode);
        }
        finally
        {
            System.out.println(String.format("%s: RET.", fn));
        }
    }

    @After
    public void After() throws Throwable
    {
        System.out.println("After");
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
