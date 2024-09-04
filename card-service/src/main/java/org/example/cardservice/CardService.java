package org.example.cardservice;

import org.example.cardservice.exception.CustomNotfoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public Card updateCard(Long id, Card updatedCard) {
        return cardRepository.findById(id)
                .map(card -> {
                    card.setCardNumber(updatedCard.getCardNumber());
                    // Update other fields as necessary
                    return cardRepository.save(card);
                })
                .orElseThrow(() -> new CustomNotfoundException("Card not found"));
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
