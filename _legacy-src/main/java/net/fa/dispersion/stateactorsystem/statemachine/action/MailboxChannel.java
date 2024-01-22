package net.fa.dispersion.stateactorsystem.statemachine.action;

import org.springframework.lang.NonNull;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class MailboxChannel<MSG> {
    private final LinkedBlockingQueue<MSG> mailbox = new LinkedBlockingQueue<>();

    public MailboxChannel<MSG> sendMessage(@NonNull final MSG message) {
        mailbox.offer(message);
        return this;
    }
}
