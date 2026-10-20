package edu.minibank.model;
import edu.minibank.exception.InvalidAmountException;
import edu.minibank.exception.InsufficientFundsException;
public interface Transactable {
    void deposit(long amount) throws InvalidAmountException;
    boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException;
}
