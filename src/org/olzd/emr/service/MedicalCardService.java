package org.olzd.emr.service;

import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.SearchByNameModel;

import java.util.Arrays;
import java.util.List;

public class MedicalCardService {
    public List<MedicalCard> findMedicalCardByName(SearchByNameModel searchByName) {
        MedicalCard cardA = new MedicalCard();
        cardA.setName("aa");
        cardA.setSurname("AAA");

        MedicalCard cardB = new MedicalCard();
        cardB.setName("bb");
        cardB.setSurname("BBB");

        return Arrays.asList(cardA, cardB);
    }

}
