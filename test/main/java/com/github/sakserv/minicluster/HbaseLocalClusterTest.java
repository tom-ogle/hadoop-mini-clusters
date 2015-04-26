/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.sakserv.minicluster;

import com.github.sakserv.minicluster.config.ConfigVars;
import com.github.sakserv.minicluster.config.PropertyParser;
import com.github.sakserv.minicluster.impl.HbaseLocalCluster;
import com.github.sakserv.minicluster.impl.ZookeeperLocalCluster;
import org.apache.hadoop.conf.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HbaseLocalClusterTest {
    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(HbaseLocalClusterTest.class);

    // Setup the property parser
    private static PropertyParser propertyParser;
    static {
        try {
            propertyParser = new PropertyParser(ConfigVars.DEFAULT_PROPS_FILE);
        } catch(IOException e) {
            LOG.error("Unable to load property file: " + propertyParser.getProperty(ConfigVars.DEFAULT_PROPS_FILE));
        }
    }

    private static HbaseLocalCluster hbaseLocalCluster;

    @BeforeClass
    public static void setUp(){
        hbaseLocalCluster = new HbaseLocalCluster.Builder()
                .setHbaseMasterPort(
                        Integer.parseInt(propertyParser.getProperty(ConfigVars.HBASE_MASTER_PORT_KEY)))
                .setHbaseMasterInfoPort(
                        Integer.parseInt(propertyParser.getProperty(ConfigVars.HBASE_MASTER_INFO_PORT_KEY)))
                .setNumRegionServers(
                        Integer.parseInt(propertyParser.getProperty(ConfigVars.HBASE_NUM_REGION_SERVERS_KEY)))
                .setHbaseRootDir(propertyParser.getProperty(ConfigVars.HBASE_ROOT_DIR_KEY))
                .setZookeeperPort(Integer.parseInt(propertyParser.getProperty(ConfigVars.ZOOKEEPER_PORT_KEY)))
                .setZookeeperConnectionString(propertyParser.getProperty(ConfigVars.ZOOKEEPER_CONNECTION_STRING_KEY))
                .setZookeeperZnodeParent(propertyParser.getProperty(ConfigVars.HBASE_ZNODE_PARENT_KEY))
                .setHbaseWalReplicationEnabled(
                        Boolean.parseBoolean(propertyParser.getProperty(ConfigVars.HBASE_WAL_REPLICATION_ENABLED_KEY)))
                .setHbaseConfiguration(new Configuration())
                .build();
    }

    @Test
    public void testHbaseMasterPort() {
        assertEquals(Integer.parseInt(propertyParser.getProperty(ConfigVars.HBASE_MASTER_PORT_KEY)),
                (int) hbaseLocalCluster.getHbaseMasterPort());
    }

    @Test
    public void testHbaseMasterInfoPort() {
        assertEquals(Integer.parseInt(propertyParser.getProperty(ConfigVars.HBASE_MASTER_INFO_PORT_KEY)),
                (int) hbaseLocalCluster.getHbaseMasterInfoPort());
    }

    @Test
    public void testHbaseNumRegionServers() {
        assertEquals(Integer.parseInt(propertyParser.getProperty(ConfigVars.HBASE_NUM_REGION_SERVERS_KEY)),
                (int) hbaseLocalCluster.getNumRegionServers());
    }

    @Test
    public void testHbaseRootDir() {
        assertEquals(propertyParser.getProperty(ConfigVars.HBASE_ROOT_DIR_KEY), hbaseLocalCluster.getHbaseRootDir());
    }

    @Test
    public void testZookeeperPort() {
        assertEquals(Integer.parseInt(propertyParser.getProperty(ConfigVars.ZOOKEEPER_PORT_KEY)),
                (int) hbaseLocalCluster.getZookeeperPort());
    }

    @Test
    public void testZookeeperConnectionString() {
        assertEquals(propertyParser.getProperty(ConfigVars.ZOOKEEPER_CONNECTION_STRING_KEY),
                hbaseLocalCluster.getZookeeperConnectionString());
    }

    @Test
    public void testZookeeperZnodeParent() {
        assertEquals(propertyParser.getProperty(ConfigVars.HBASE_ZNODE_PARENT_KEY),
                hbaseLocalCluster.getZookeeperZnodeParent());
    }

    @Test
    public void testHbaseWalReplicationEnabled() {
        assertEquals(Boolean.parseBoolean(propertyParser.getProperty(ConfigVars.HBASE_WAL_REPLICATION_ENABLED_KEY)),
                (boolean) hbaseLocalCluster.getHbaseWalReplicationEnabled());
    }

    @Test
    public void testHdfsConf() {
        assertTrue(hbaseLocalCluster.getHbaseConfiguration() instanceof org.apache.hadoop.conf.Configuration);

    }
}
