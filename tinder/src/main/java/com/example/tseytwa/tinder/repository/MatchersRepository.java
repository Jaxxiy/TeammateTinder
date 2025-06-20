package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MatchersRepository extends JpaRepository<Match, Integer> {

    Set<Match> findAllByProfile1Id(int id);

    List<Match> findAllByProfile2Id(int id);
}
