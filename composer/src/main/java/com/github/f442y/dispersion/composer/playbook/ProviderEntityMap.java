//package com.github.f442y.dispersion.composer.playbook;
//
//import com.github.f442y.dispersion.composer.model.Play;
//import com.github.f442y.dispersion.core.orchestration.OrchestrationEntity;
//
//import java.util.HashMap;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Map of maps of maps
// * <p>
// * Map of entities, with a nested map of Plays expected to run for that entity, with a nested map of expected zones for that Play
// * <p>
// * { entity : { expectedPlay : { expectedZone : State } } }
// *
// * @param <ORCHESTRATION_ENTITY>
// */
//public final class ProviderEntityMap<ORCHESTRATION_ENTITY extends OrchestrationEntity> {
//    private final ConcurrentHashMap<ORCHESTRATION_ENTITY, ExpectedPlaysMap> entityMap = new ConcurrentHashMap<>();
//
//    public ExpectedPlaysMap addEntity(ORCHESTRATION_ENTITY orchestrationEntity) {
//        return entityMap.putIfAbsent(orchestrationEntity, new ExpectedPlaysMap());
//    }
//
//    public QueryForEntity forEntity(ORCHESTRATION_ENTITY orchestrationEntity) {
//        return new QueryForEntity(this.entityMap.get(orchestrationEntity).expectedPlaysMap);
//    }
//
//    public static final class QueryForEntity {
//        private final HashMap<Play<?, ?>, ExpectedZonesMap> expectedPlaysMap;
//
//        public QueryForEntity(HashMap<Play<?, ?>, ExpectedZonesMap> expectedPlaysMap) {
//            this.expectedPlaysMap = expectedPlaysMap;
//        }
//
//        // play must have at least one zone, or it will never run
//        public void addExpectedZonePlay(Play<?, ?> play, String zone) {
//            // if this is the first time this play is added for this entity, add the play with an empty zone map
//            this.expectedPlaysMap.putIfAbsent(play, new ExpectedZonesMap());
//            this.expectedPlaysMap.get(play).expectedZonesMap.put(zone, State.PENDING);
//        }
//
//        public void updateZonePlayState(Play<?, ?> play, String zone, State newState) {
//            this.expectedPlaysMap.get(play).expectedZonesMap.replace(zone, newState);
//        }
//
//        public State getZonePlayState(Play<?, ?> play, String zone) {
//            return this.expectedPlaysMap.get(play).expectedZonesMap.get(zone);
//        }
//    }
//
//    public static final class ExpectedPlaysMap {
//        private final HashMap<Play<?, ?>, ExpectedZonesMap> expectedPlaysMap = new HashMap<>();
//    }
//
//    public static final class ExpectedZonesMap {
//        private final HashMap<String, State> expectedZonesMap = new HashMap<>();
//    }
//
//    public enum State {
//        PENDING, RUNNING, FAILED, COMPLETE,
//    }
//}
