//package net.fa.dispersion.stateactorsystem;
//
//import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ActiveMQArtemisConfig implements ArtemisConfigurationCustomizer {
//    @Override
//    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
//        try {
//            configuration.addAcceptorConfiguration("remote", "tcp://0.0.0.0:61618");
////            configuration.addClusterConfiguration()
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
