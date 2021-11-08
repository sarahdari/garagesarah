package org.acme.service;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Uni;
import org.acme.Auto;
import org.acme.database.DBList;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AutoService {

    DBList db = DBList.getInstance();


    @Inject
    RedisClient redisClient;


    public void addAuto(Auto auto) {
        HashSet<Auto> garage = new HashSet<>();
        garage.add(auto);
    }

    /*Uni<Void> del(String key) {
        return reactiveRedisClient.del(Arrays.asList(key))
                .map(response -> null);
    }*/

    String get(String key) {
        return redisClient.get(key).toString();
    }

}










