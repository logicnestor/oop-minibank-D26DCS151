package edu.minibank.service;

import edu.minibank.model.Account;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class StatePersister {
    private StatePersister() {}
    public static void save(Account[] accounts, Path file) throws IOException {
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(accounts);
        }
    }
    public static Account[] load(Path file) throws IOException {
        if (!Files.exists(file)) return new Account[0];
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file))) {
            Object obj;
            try { obj = in.readObject(); }
            catch (ClassNotFoundException e) { throw new IOException("Unknown class in saved state", e); }
            if (!(obj instanceof Account[] accounts)) throw new IOException("Invalid saved account data");
            return accounts;
        }
    }
}
