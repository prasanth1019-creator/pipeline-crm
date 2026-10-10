package com.pipelinecrm.config;

import com.pipelinecrm.model.Company;
import com.pipelinecrm.model.Contact;
import com.pipelinecrm.model.Deal;
import com.pipelinecrm.model.DealStage;
import com.pipelinecrm.repository.CompanyRepository;
import com.pipelinecrm.repository.ContactRepository;
import com.pipelinecrm.repository.DealRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Puts some sample data into the database when the app starts, so the APIs are not empty. */
@Component
public class DataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final DealRepository dealRepository;

    public DataLoader(CompanyRepository companyRepository,
                      ContactRepository contactRepository,
                      DealRepository dealRepository) {
        this.companyRepository = companyRepository;
        this.contactRepository = contactRepository;
        this.dealRepository = dealRepository;
    }

    @Override
    public void run(String... args) {
        if (companyRepository.count() > 0) {
            return; // already has data, do nothing
        }

        Company acme = companyRepository.save(new Company("Acme Traders", "Retail", "https://acme.example.com"));
        Company nellai = companyRepository.save(new Company("Nellai Textiles", "Manufacturing", "https://nellai.example.com"));
        Company bright = companyRepository.save(new Company("BrightEdu", "Education", "https://brightedu.example.com"));

        Contact ravi = contact("Ravi", "Kumar", "ravi@acme.example.com", "9876543210", acme);
        Contact meena = contact("Meena", "Sundar", "meena@nellai.example.com", "9876501234", nellai);
        Contact arun = contact("Arun", "Raj", "arun@nellai.example.com", "9123456780", nellai);
        Contact divya = contact("Divya", "Lakshmi", "divya@brightedu.example.com", "9988776655", bright);

        deal("Acme annual license", "250000.00", DealStage.NEGOTIATION, ravi);
        deal("Nellai CRM rollout", "480000.00", DealStage.PROPOSAL, meena);
        deal("Nellai add-on seats", "75000.00", DealStage.NEW, arun);
        deal("BrightEdu pilot", "120000.00", DealStage.QUALIFIED, divya);
    }

    private Contact contact(String first, String last, String email, String phone, Company company) {
        Contact c = new Contact();
        c.setFirstName(first);
        c.setLastName(last);
        c.setEmail(email);
        c.setPhone(phone);
        c.setCompany(company);
        return contactRepository.save(c);
    }

    private void deal(String title, String amount, DealStage stage, Contact contact) {
        Deal d = new Deal();
        d.setTitle(title);
        d.setAmount(new BigDecimal(amount));
        d.setStage(stage);
        d.setContact(contact);
        dealRepository.save(d);
    }
}
