package com.codingwithmyself.graphqldemo.service;

import com.codingwithmyself.graphqldemo.model.Player;
import com.codingwithmyself.graphqldemo.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {

    private List<Player> players = new ArrayList<>();

    //this helps to  get the id
    AtomicInteger id = new AtomicInteger(0);

    public List<Player> findAll() {
        return players;
    }

    public Optional<Player> findOne(Integer id) {
        return players.stream()
                .filter(player -> player.Id() == id).findFirst();
    }


    public Player create(String name, Team team) {
        Player player = new Player(id.incrementAndGet(), name, team);
        players.add(player);
        return player;
    }


    public Player update(Integer id, String name, Team team) {
        Player updatedPlayer = new Player(id, name, team);
        Optional<Player> optionalPlayer = players.stream().filter(p -> p.Id() == id).findFirst();
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            int index = players.indexOf(player);
            players.set(index, updatedPlayer);
        } else {
            throw new IllegalArgumentException("Invalid Player");
        }
        return updatedPlayer;
    }

    public Player delete(Integer id) {
        Player player = players.stream().filter(p -> p.Id() == id).findFirst().orElseThrow(
                () -> new IllegalArgumentException("Player with this id doesnt exist")
        );
        players.remove(player);
        return player;
    }

    @PostConstruct
    public void init() {
        players.add(new Player(id.incrementAndGet(), "Antonela", Team.DC));
        players.add(new Player(id.incrementAndGet(), "Arber", Team.DC));
        players.add(new Player(id.incrementAndGet(), "Bushi", Team.DC));
        players.add(new Player(id.incrementAndGet(), "Geri", Team.DC));
        players.add(new Player(id.incrementAndGet(), "Leda", Team.DC));
    }
}
