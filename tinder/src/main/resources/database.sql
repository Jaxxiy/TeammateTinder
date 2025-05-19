-- users table
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(255)
);

-- profiles table
CREATE TABLE profiles (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          age INTEGER,
                          user_id INTEGER REFERENCES users(id) ON DELETE CASCADE
);

-- work_experience table
CREATE TABLE work_experience (
                                 id SERIAL PRIMARY KEY,
                                 user_id INTEGER REFERENCES profiles(id),
                                 company VARCHAR(255),
                                 description VARCHAR(255),
                                 post VARCHAR(255),
                                 started_at TIMESTAMP,
                                 ended_at TIMESTAMP
);

-- links table
CREATE TABLE links (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER REFERENCES profiles(id),
                       social VARCHAR(255),
                       link VARCHAR(255)
);

-- likes table
CREATE TABLE likes (
                       id SERIAL PRIMARY KEY,
                       liker_id INTEGER REFERENCES profiles(id),
                       likeable_id INTEGER REFERENCES profiles(id)
);

-- matchers table
CREATE TABLE matchers (
                          id SERIAL PRIMARY KEY,
                          profile1 INTEGER REFERENCES profiles(id),
                          profile2 INTEGER REFERENCES profiles(id)
);

-- features table
CREATE TABLE features (
                          id SERIAL PRIMARY KEY,
                          liker_id INTEGER REFERENCES profiles(id),
                          likeable_id INTEGER REFERENCES profiles(id)
);

-- chat table
CREATE TABLE chat (
                      id SERIAL PRIMARY KEY,
                      user_id INTEGER REFERENCES profiles(id),
                      user_with INTEGER REFERENCES profiles(id)
);

-- chat_messages table
CREATE TABLE chat_message (
                              id SERIAL PRIMARY KEY,
                              chat_id INTEGER REFERENCES profiles(id),
                              message VARCHAR(255),
                              created_at TIMESTAMP
);

CREATE TABLE skills
(
    id   SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES profiles(id),
    name VARCHAR(255)
);

drop table skills;
drop table matchers;