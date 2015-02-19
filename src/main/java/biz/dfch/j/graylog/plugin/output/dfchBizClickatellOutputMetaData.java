package biz.dfch.j.graylog.plugin.output;

import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.Version;

import java.net.URI;
import java.util.Set;

/**
 * Implement the PluginMetaData interface here.
 */
public class dfchBizClickatellOutputMetaData implements PluginMetaData
{
    @Override
    public String getUniqueId()
    {
        return "dfchBizClickatellOutputPlugin";
    }

    @Override
    public String getName()
    {
        return "d-fens Clickatell Output";
    }

    @Override
    public String getAuthor()
    {
        return "Ronald Rink, d-fens GmbH";
    }

    @Override
    public URI getURL()
    {
        return URI.create("http://d-fens.ch");
    }

    @Override
    public Version getVersion()
    {
        return new Version(1, 0, 0);
    }

    @Override
    public String getDescription()
    {
        return "d-fens plugin for sending short messages (SMS) via Clickatell";
    }

    @Override
    public Version getRequiredVersion()
    {
        return new Version(1, 0, 0);
    }

    @Override
    public Set<ServerStatus.Capability> getRequiredCapabilities()
    {
        return java.util.EnumSet.of(ServerStatus.Capability.SERVER);
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
