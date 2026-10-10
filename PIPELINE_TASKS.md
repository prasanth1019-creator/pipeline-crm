# PIPELINE_TASKS.md: Sprint 1 (Tickets 1-10)

Project: `pipeline-crm` (Company, Contact, Deal). Stack: Java 17, Spring Boot 3.3.5, Maven, H2.

Indha file ah `C:\pipeline-crm\pipeline-crm\` (pom.xml irukkura folder) la paste pannunga. IntelliJ la `PIPELINE_TASKS.md` ah right-click > Open in > Split Right pannina, oru pakkam tickets, innoru pakkam code.

Test requests ellam `requests.http` file la irukku. Adhaiyum same folder la paste pannunga. IntelliJ la open pannina, ovvoru request kum pakkathula green arrow varum, click pannina request poidum.

**Note:** Naan inga compile pannittu test panna mudiyala. Code ah kavanama ezhudhirukken, aana oru ticket la error vandha "Ticket X, step Y la intha error" nu full error text oda anuppunga.

---

## Ticket 0: Git setup (oru thadavai mattum)

### Yen Git?
Video game la "save point" irukkum la? Level mudichu save pannuvom, adutha level la thappa pannina, save point ku thirumbi poiduvom. **Git = namma code ku save points.** Ovvoru save point ku peru **commit**.

Real company la 10 per same project la velai seiyuraanga. Ellarum same file ah maathina mothama gundu aagidum. Adhanaala:
- **main branch** = "sariyaana, velai seiyura code". Yaarum direct ah ithula velai seiya maatanga.
- **feature branch** = main oda oru copy. Neenga ungal ticket ku thani copy la velai seiveenga.
- Velai mudinja, **Pull Request (PR)** podureenga: "en maatrangala paathu main la serunga" nu. Team lead paathu OK sonna dhaan main la serum.

**GitHub** = Git code ah internet la vaikkura idam (Google Drive maari, but code ku).

### Steps
1. **Git install:** https://git-scm.com/download/win la download pannitu install (ellathukum Next). IntelliJ ah close pannitu thirumba open pannunga.
2. IntelliJ la keezha **Terminal** tab click pannunga (illa `Alt+F12`). Idhu project folder la open aagum. Idhula type pannunga:
```
git --version
git config --global user.name "Prasanth"
git config --global user.email "unga-email@example.com"
```
(Ungal GitHub email use pannunga.)
3. Project la Git start pannunga:
```
git init -b main
git status
```
`git status` la `target/` `.idea/` kaattakoodaadhu (adhellam `.gitignore` la irukku, athanaala ignore aagum). `src`, `pom.xml` mattum red ah theriyanum.
4. Mudhal save point:
```
git add .
git commit -m "Initial project setup"
git log --oneline
```
`git add .` = "ella maatrangaleyum save ku ready pannu". `git commit` = "save pannu". `-m` = save ku oru peru.
5. **GitHub:** https://github.com la account illa na create pannunga. Right-top `+` > New repository > Name: `pipeline-crm` > **README, .gitignore ethuvum add pannaadheenga** (empty ah irukkanum) > Create.
6. Terminal la (`<username>` ah ungal GitHub username ah maathunga):
```
git remote add origin https://github.com/<username>/pipeline-crm.git
git push -u origin main
```
Login window / browser vandha GitHub la sign in pannunga. (GitHub la password direct ah work aagaadhu, browser login thaan.)
7. GitHub page refresh pannunga. Ungal code theriyum.

### Daily loop (ovvoru ticket kum idhe dhaan)
```
git switch main
git pull
git switch -c feature/PIPE-1-company-city     # ticket ku thani branch
... code maatrunga, run pannunga, test pannunga ...
git status                                     # enna maaritchu nu paaru
git add .
git commit -m "PIPE-1: Add city to company"
git push -u origin feature/PIPE-1-company-city
```
Appram GitHub la manja color **"Compare & pull request"** button varum > click > title check pannunga > **Create pull request**. Company la teammate review pannuvaanga. Neenga thaniya practice panradhaala neengale **Merge pull request** > **Confirm merge** click pannunga, appram **Delete branch**. Kadaisiya:
```
git switch main
git pull
```
(main la ippo ungal maatram vandhirukkum.)

IntelliJ UI la idhe velai: `Alt+0` = Commit panel, `Ctrl+Shift+K` = Push, branch peru bottom-right la irukkum, click panna New Branch.

**Rules:** main la direct ah commit pannaadheenga. Oru ticket = oru branch = oru PR. Commit message la ticket number ezhudhunga.

---

## Ticket 1: Company ku `city` add pannunga

**Branch:** `feature/PIPE-1-company-city`

> **Manager:** "Sales team ku companies ah city wise paakkanum. Company la `city` field add pannunga (optional). Create pannumbodhu city anuppalaam, response la varanum."

### Yen idhellam? (15 vayasu version)
Company oru "form" maari. Ippo form la Name, Industry, Website mattum irukku. City kekkanum na form la pudhu kattam (field) serkkanum. Aana oru kattam serkka oru edathula mattum pothaadhu, 4 edam maatranum:
1. **Entity** (`Company`): database la irukkura column. Idhula serkkaama database ku theriyaadhu.
2. **Request DTO**: user anuppura data. Idhula serkkaama user city anuppina namma edukkamaattom.
3. **Response DTO**: namma thirumba kudukkura data. Idhula serkkaama city user ku theriyaadhu.
4. **Service**: request la irundhu entity ku city ah kondu poradhu.

DTO = "Data Transfer Object" = anuppura / vaangura parcel. Entity ah direct ah veliya kudukkaama parcel la pottu thaan kudukkanum, apdi na database oda ulla vishayam veliya leak aagaadhu.

### IntelliJ steps
1. `Ctrl+Shift+N` > `Company.java` open pannunga. `website` field keezha add pannunga:
```java
    private String city;
```
2. Adhe file la constructor ah maathunga (3 parameter la irundhu 4 ku):
```java
    public Company(String name, String industry, String website, String city) {
        this.name = name;
        this.industry = industry;
        this.website = website;
        this.city = city;
    }
```
3. Getter/setter serkunga (`getWebsite` ku keezha):
```java
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
```
4. `CompanyRequest.java` ah muzhuvadhum ippadi maathunga:
```java
package com.pipelinecrm.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(
        @NotBlank(message = "name is required") String name,
        String industry,
        String website,
        String city) {
}
```
5. `CompanyResponse.java` ah muzhuvadhum maathunga:
```java
package com.pipelinecrm.dto;

import com.pipelinecrm.model.Company;
import java.time.LocalDateTime;

public record CompanyResponse(
        Long id,
        String name,
        String industry,
        String website,
        String city,
        LocalDateTime createdAt) {

    public static CompanyResponse from(Company c) {
        return new CompanyResponse(c.getId(), c.getName(), c.getIndustry(), c.getWebsite(),
                c.getCity(), c.getCreatedAt());
    }
}
```
6. `CompanyService.java` la `create` method la `new Company(...)` line ah maathunga:
```java
        Company company = new Company(request.name(), request.industry(), request.website(), request.city());
```
7. `DataLoader.java` la 3 `new Company(...)` line um 4th parameter kekkum (illana red error varum). Maathunga:
```java
        Company acme = companyRepository.save(new Company("Acme Traders", "Retail", "https://acme.example.com", "Chennai"));
        Company nellai = companyRepository.save(new Company("Nellai Textiles", "Manufacturing", "https://nellai.example.com", "Tirunelveli"));
        Company bright = companyRepository.save(new Company("BrightEdu", "Education", "https://brightedu.example.com", "Coimbatore"));
```
8. App ah stop (red square) pannitu thirumba run pannunga.

### Test
`requests.http` > **T1**. List la `city` theriyanum. Oru pudhu company `city` oda create pannunga.

**Commit:** `PIPE-1: Add city to company`, appram Daily loop padi PR.

**Kathukittadhu:** Oru field = Entity + Request + Response + Service. Constructor maatha, adhai use panra ella edathulayum maatranum (compiler error ve nama ku vazhi kaattum).

---

## Ticket 2: Contact phone number validation (10 digit)

**Branch:** `feature/PIPE-2-phone-validation`

> **Manager:** "Contacts la thappa phone number (abc, 123) save aagudhu. Phone optional, aana kudutha 10 digit Indian mobile number ah irukkanum (6-9 la start aagi). Thappa na 400 error + clear message. Test um ezhudhunga."

### Yen idhellam?
Nee WhatsApp la phone number type panna "abc" nu poda mudiyaadhu la? Adhu maari doorman. Data database ku pogura munnadiye "idhu sariyaana data va?" nu check pannanum. Check pannama vitta, sales team call pannum bodhu "number illa" nu theriyum, nashtam.

`@Pattern` = "indha format ku match aanaa thaan allow pannu". Regex `^[6-9]\d{9}$` nu na: `^` thodakkam, `[6-9]` mudhal digit 6 la irundhu 9 varai, `\d{9}` innum 9 digits, `$` mudivu. Mothama 10 digits.

### IntelliJ steps
1. `ContactRequest.java` la phone ah maathunga (import um add pannunga):
```java
import jakarta.validation.constraints.Pattern;
```
```java
        @Pattern(regexp = "^[6-9]\\d{9}$", message = "phone must be a 10-digit mobile number")
        String phone,
```
(Java string la `\` rendu thadavai ezhudhanum: `\\d`.)

Phone `null` na `@Pattern` check pannaadhu, so optional ah thaan irukkum.

2. Test file: `src/test/java/com/pipelinecrm/controller/` folder create pannunga (right-click `test/java/com/pipelinecrm` > New > Package > `controller`). Adhula `ContactControllerTest.java`:
```java
package com.pipelinecrm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pipelinecrm.dto.ContactResponse;
import com.pipelinecrm.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    private String json(String phone) {
        return """
                {"firstName":"Ravi","lastName":"Kumar","email":"ravi@test.com",
                 "phone":%s,"companyId":1}
                """.formatted(phone);
    }

    @Test
    void invalidPhoneReturns400() throws Exception {
        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("\"12345\"")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.phone").value("phone must be a 10-digit mobile number"));
    }

    @Test
    void validPhoneReturns201() throws Exception {
        when(contactService.create(any()))
                .thenReturn(new ContactResponse(1L, "Ravi", "Kumar", "ravi@test.com", "9876543210", 1L, "Acme"));

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("\"9876543210\"")))
                .andExpect(status().isCreated());
    }

    @Test
    void missingPhoneIsAllowed() throws Exception {
        when(contactService.create(any()))
                .thenReturn(new ContactResponse(1L, "Ravi", "Kumar", "ravi@test.com", null, 1L, "Acme"));

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("null")))
                .andExpect(status().isCreated());
    }
}
```
3. Test class ku pakkathula green arrow > Run. 3 um green ah irukkanum.

### Test
`requests.http` > **T2**: thappa phone (400 varanum), sariyaana phone (201 varanum).

**Commit:** `PIPE-2: Validate contact phone number`

**Kathukittadhu:** `@Valid` + `@Pattern` = vaasal la doorman. `@WebMvcTest` = database illaama, controller mattum thani ah test panra tool. `@MockBean` = "unmai service ku bathila poli (fake) service use pannu".

---

## Ticket 3: Contact notes: add, list, delete

**Branch:** `feature/PIPE-3-contact-notes`

> **Manager:** "Sales people calls pannitu notes ezhudhuvaanga. Contact oda notes ah add pannanum, list paakkanum, thappa ezhudhina delete pannanum. Note illana 404."

### Yen idhellam?
Notes = contact ku "sticky note". Oru contact ku neraiya notes (**One-to-Many**). Note la `contact_id` nu column irukkum, adhu "indha note yaarudaiyadhu" nu sollum (foreign key).

DELETE endpoint la mukkiyamaana vishayam: illaadha note ah delete panna sonna **404** thirumba kudukkanum, "delete aagiduchu" nu poi solla koodaadhu. Delete success na **204 No Content** ("velai mudinjuchu, kaattura idhu onnum illa").

### IntelliJ steps
Pudhu 6 file create pannunga (right-click package > New > Java Class).

1. `model/Note.java`:
```java
package com.pipelinecrm.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Note() {
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
```
2. `repository/NoteRepository.java`:
```java
package com.pipelinecrm.repository;

import com.pipelinecrm.model.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByContactIdOrderByCreatedAtDesc(Long contactId);
}
```
(Method peru ah padinga: find By ContactId OrderBy CreatedAt Desc. Spring peru la irundhe query ezhudhidum!)

3. `dto/NoteRequest.java`:
```java
package com.pipelinecrm.dto;

import jakarta.validation.constraints.NotBlank;

public record NoteRequest(@NotBlank(message = "text is required") String text) {
}
```
4. `dto/NoteResponse.java`:
```java
package com.pipelinecrm.dto;

import com.pipelinecrm.model.Note;
import java.time.LocalDateTime;

public record NoteResponse(Long id, String text, Long contactId, LocalDateTime createdAt) {

    public static NoteResponse from(Note n) {
        return new NoteResponse(n.getId(), n.getText(), n.getContact().getId(), n.getCreatedAt());
    }
}
```
5. `service/NoteService.java`:
```java
package com.pipelinecrm.service;

import com.pipelinecrm.dto.NoteRequest;
import com.pipelinecrm.dto.NoteResponse;
import com.pipelinecrm.exception.ResourceNotFoundException;
import com.pipelinecrm.model.Contact;
import com.pipelinecrm.model.Note;
import com.pipelinecrm.repository.ContactRepository;
import com.pipelinecrm.repository.NoteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final ContactRepository contactRepository;

    public NoteService(NoteRepository noteRepository, ContactRepository contactRepository) {
        this.noteRepository = noteRepository;
        this.contactRepository = contactRepository;
    }

    @Transactional
    public NoteResponse create(Long contactId, NoteRequest request) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id " + contactId));
        Note note = new Note();
        note.setText(request.text());
        note.setContact(contact);
        return NoteResponse.from(noteRepository.save(note));
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getForContact(Long contactId) {
        if (!contactRepository.existsById(contactId)) {
            throw new ResourceNotFoundException("Contact not found with id " + contactId);
        }
        return noteRepository.findByContactIdOrderByCreatedAtDesc(contactId)
                .stream().map(NoteResponse::from).toList();
    }

    @Transactional
    public void delete(Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new ResourceNotFoundException("Note not found with id " + noteId);
        }
        noteRepository.deleteById(noteId);
    }
}
```
6. `controller/NoteController.java`:
```java
package com.pipelinecrm.controller;

import com.pipelinecrm.dto.NoteRequest;
import com.pipelinecrm.dto.NoteResponse;
import com.pipelinecrm.service.NoteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/contacts/{contactId}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse create(@PathVariable Long contactId, @Valid @RequestBody NoteRequest request) {
        return noteService.create(contactId, request);
    }

    @GetMapping("/contacts/{contactId}/notes")
    public List<NoteResponse> getForContact(@PathVariable Long contactId) {
        return noteService.getForContact(contactId);
    }

    @DeleteMapping("/notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long noteId) {
        noteService.delete(noteId);
    }
}
```
7. Stop > Run. Console la `create table notes` theriyanum.

### Test
`requests.http` > **T3**: note add > list > delete > thirumba delete (404 varanum).

**Commit:** `PIPE-3: Add contact notes with delete`

**Kathukittadhu:** Pudhu feature = Entity > Repository > DTO > Service > Controller (indha order thaan ella velaikkum). DELETE: illana 404, success na 204.

---

## Ticket 4: Dashboard stats

**Branch:** `feature/PIPE-4-dashboard`

> **Manager:** "Home screen la oru dashboard kaattanum: mothama companies, contacts, deals count; ovvoru stage la ethanai deals; WON deals oda total amount; innum close aagaadha (open) deals oda total amount."

### Yen idhellam?
Boss ku "mothama enna nadakkudhu" nu oru paarvai la theriyanum. Namma ellaa deals ah Java la edukkama, **database kitte kanakku keppom** (count, sum). Database athukku thaan nalla, 10 lakh rows ku kooda vegama solluvaan. Java la ellathaiyum izhuthu count pannina memory poidum.

### IntelliJ steps
1. `DealRepository.java` ah muzhuvadhum maathunga:
```java
package com.pipelinecrm.repository;

import com.pipelinecrm.model.Deal;
import com.pipelinecrm.model.DealStage;
import java.math.BigDecimal;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DealRepository extends JpaRepository<Deal, Long> {

    long countByStage(DealStage stage);

    @Query("select sum(d.amount) from Deal d where d.stage = :stage")
    BigDecimal sumAmountByStage(@Param("stage") DealStage stage);

    @Query("select sum(d.amount) from Deal d where d.stage not in :stages")
    BigDecimal sumAmountByStageNotIn(@Param("stages") Collection<DealStage> stages);
}
```
2. `dto/DashboardResponse.java`:
```java
package com.pipelinecrm.dto;

import com.pipelinecrm.model.DealStage;
import java.math.BigDecimal;
import java.util.Map;

public record DashboardResponse(
        long totalCompanies,
        long totalContacts,
        long totalDeals,
        Map<DealStage, Long> dealsByStage,
        BigDecimal wonAmount,
        BigDecimal openPipelineAmount) {
}
```
3. `service/DashboardService.java`:
```java
package com.pipelinecrm.service;

import com.pipelinecrm.dto.DashboardResponse;
import com.pipelinecrm.model.DealStage;
import com.pipelinecrm.repository.CompanyRepository;
import com.pipelinecrm.repository.ContactRepository;
import com.pipelinecrm.repository.DealRepository;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final DealRepository dealRepository;

    public DashboardService(CompanyRepository companyRepository,
                            ContactRepository contactRepository,
                            DealRepository dealRepository) {
        this.companyRepository = companyRepository;
        this.contactRepository = contactRepository;
        this.dealRepository = dealRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getStats() {
        Map<DealStage, Long> byStage = new EnumMap<>(DealStage.class);
        for (DealStage stage : DealStage.values()) {
            byStage.put(stage, dealRepository.countByStage(stage));
        }

        BigDecimal won = orZero(dealRepository.sumAmountByStage(DealStage.WON));
        BigDecimal open = orZero(dealRepository.sumAmountByStageNotIn(List.of(DealStage.WON, DealStage.LOST)));

        return new DashboardResponse(
                companyRepository.count(),
                contactRepository.count(),
                dealRepository.count(),
                byStage,
                won,
                open);
    }

    private BigDecimal orZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
```
(`sum` ku deals illana database `null` kudukkum. Adhanaala `orZero`.)

4. `controller/DashboardController.java`:
```java
package com.pipelinecrm.controller;

import com.pipelinecrm.dto.DashboardResponse;
import com.pipelinecrm.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getStats() {
        return dashboardService.getStats();
    }
}
```
5. Stop > Run.

### Test
`requests.http` > **T4**. Seed data la: totalDeals 4, NEGOTIATION 1, PROPOSAL 1, NEW 1, QUALIFIED 1, wonAmount 0, openPipelineAmount 925000.00 varanum. (250000 + 480000 + 75000 + 120000.)

**Commit:** `PIPE-4: Add dashboard stats endpoint`

**Kathukittadhu:** Kanakku database kitta kelunga (`count`, `sum`). `@Query` la namma SQL maari (JPQL) ezhudhalaam.

---

## Ticket 5: Deals list ku pagination + stage filter

**Branch:** `feature/PIPE-5-deal-pagination`

> **Manager:** "Production la 50,000 deals irukku, `/api/deals` ellaathaiyum kudukkudhu, page hang aagudhu. Pagination add pannunga: default 10 per page, max 50, latest first. Stage wise filter um venum."

### Yen idhellam?
Google la search pannina 1 crore results ah oru page la kaattuvaana? Illa, 10 10 ah "page" ah pirichu kaattuvaan. Adhu pagination. `page=0` (mudhal page), `size=10` (oru page la 10), `sort=createdAt,desc` (pudhusu mudhal).

Spring la `Pageable` nu oru object irukku, URL la `?page=0&size=10` vandha adhai thaane eduthukkum. Neenga adhai repository ku pass pannina pothum. `size=100000` nu yaaravadhu anuppi server ah kollaama irukka **max size** vaikkanum.

### IntelliJ steps
1. `application.properties` la keezha oru line add pannunga:
```
spring.data.web.pageable.max-page-size=50
```
2. `DealRepository.java` la imports la add pannunga:
```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
```
Interface kulla add pannunga:
```java
    Page<Deal> findByStage(DealStage stage, Pageable pageable);
```
3. `DealService.java` la imports add pannunga:
```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
```
Pazhaya `getAll()` method ah delete pannitu idhai podunga:
```java
    @Transactional(readOnly = true)
    public Page<DealResponse> getAll(DealStage stage, Pageable pageable) {
        Page<Deal> page = (stage == null)
                ? dealRepository.findAll(pageable)
                : dealRepository.findByStage(stage, pageable);
        return page.map(DealResponse::from);
    }
```
4. `DealController.java` la imports add pannunga:
```java
import com.pipelinecrm.model.DealStage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PageableDefault;
```
Pazhaya `getAll()` method ah maathunga:
```java
    @GetMapping
    public PagedModel<DealResponse> getAll(
            @RequestParam(required = false) DealStage stage,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return new PagedModel<>(dealService.getAll(stage, pageable));
    }
```
5. Stop > Run.

### Test
`requests.http` > **T5**: `?size=2`, `?size=2&page=1`, `?stage=NEW`, `?size=500` (max 50 ah kuraichidum).

Response la `content` (deals), `page` (`size`, `number`, `totalElements`, `totalPages`) varum.

**Commit:** `PIPE-5: Add pagination and stage filter to deals`

**Kathukittadhu:** List API ku pagination kandippa venum. `Pageable` + `Page` + max size limit.

---

## Ticket 6: Deal edit (PUT) + delete

**Branch:** `feature/PIPE-6-deal-update-delete`

> **Manager:** "Sales rep deal title / amount thappa potta correct panna mudiyanum (PUT). Thevai illaadha deal ah delete panna mudiyanum. Deal illana 404."

### Yen idhellam?
- **POST** = pudhusa create. **GET** = paaru. **PUT** = ulla irukkurathai maathu. **DELETE** = azhichidu.
- Stage maatra PUT la anumadhi illa (adhukku Ticket 7 la thani PATCH varum). Adhanaala PUT ku thani request class (`DealUpdateRequest`): title + amount mattum.
- `update` la `save()` kooda thevai illa. `@Transactional` ulla entity ah maatrina, method mudiyum bodhu JPA thaane database la update pannidum (dirty checking: "entity maaritchu nu naan gavanichuten").

### IntelliJ steps
1. `dto/DealUpdateRequest.java`:
```java
package com.pipelinecrm.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DealUpdateRequest(
        @NotBlank(message = "title is required") String title,
        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.0", message = "amount cannot be negative") BigDecimal amount) {
}
```
2. `DealService.java` la `getById` method ah maathi, keezha 3 method um add pannunga:
```java
    @Transactional(readOnly = true)
    public DealResponse getById(Long id) {
        return DealResponse.from(findDeal(id));
    }

    @Transactional
    public DealResponse update(Long id, DealUpdateRequest request) {
        Deal deal = findDeal(id);
        deal.setTitle(request.title());
        deal.setAmount(request.amount());
        return DealResponse.from(deal);
    }

    @Transactional
    public void delete(Long id) {
        dealRepository.delete(findDeal(id));
    }

    private Deal findDeal(Long id) {
        return dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found with id " + id));
    }
```
Import add: `import com.pipelinecrm.dto.DealUpdateRequest;`
3. `DealController.java` la imports add:
```java
import com.pipelinecrm.dto.DealUpdateRequest;
```
Methods add:
```java
    @PutMapping("/{id}")
    public DealResponse update(@PathVariable Long id, @Valid @RequestBody DealUpdateRequest request) {
        return dealService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        dealService.delete(id);
    }
```
4. Stop > Run.

### Test
`requests.http` > **T6**: title maathu, amount negative (400), illaadha id (404), delete.

**Commit:** `PIPE-6: Add deal update and delete`

**Kathukittadhu:** CRUD = Create, Read, Update, Delete. Oru logic (`findDeal`) 3 edathula thevai na thani private method ah edunga, copy-paste pannaadheenga.

---

## Ticket 7: Deal stage business rules (PATCH, HTTP 422)

**Branch:** `feature/PIPE-7-deal-stage-rules`

> **Manager:** "Deal stage ah ishta padi maatha koodaadhu. Rules:
> 1. Order: NEW > QUALIFIED > PROPOSAL > NEGOTIATION > WON. Stage ah skip panna koodaadhu.
> 2. Open stage la irundhu eppo vena LOST aagalaam.
> 3. WON / LOST aana appram maatha koodaadhu.
> 4. Amount 0 irundha WON aaga koodaadhu.
> 5. Closed (WON/LOST) deal ah edit panna koodaadhu.
> Rule break aana HTTP 422."

### Yen idhellam?
Pizza order maari: Ordered > Cooking > Out for delivery > Delivered. "Delivered" aana pizza ah thirumba "Cooking" ku maatha mudiyaadhu la? Adhu **business rule**.

**HTTP 400** = "nee anuppina data format thappu" (phone "abc"). **HTTP 422** = "format sari, aana business rule padi mudiyaadhu" (NEW la irundhu direct WON). Rendukkum difference theriya vechukkanum.

Rules ah `DealStage` enum kulla vachaa, ellaarum use pannalaam, test panna easy.

**PATCH** = oru chinna part ah mattum maatha (stage mattum). PUT = muzhu object.

### IntelliJ steps
1. `DealStage.java` ah muzhuvadhum maathunga:
```java
package com.pipelinecrm.model;

public enum DealStage {
    NEW,
    QUALIFIED,
    PROPOSAL,
    NEGOTIATION,
    WON,
    LOST;

    public boolean isClosed() {
        return this == WON || this == LOST;
    }

    /** Order of the enum values matters here: each stage can only move to the very next one (or to LOST). */
    public boolean canMoveTo(DealStage next) {
        if (isClosed()) {
            return false;
        }
        if (next == LOST) {
            return true;
        }
        return next.ordinal() == this.ordinal() + 1;
    }
}
```
2. `exception/InvalidDealOperationException.java`:
```java
package com.pipelinecrm.exception;

public class InvalidDealOperationException extends RuntimeException {
    public InvalidDealOperationException(String message) {
        super(message);
    }
}
```
3. `GlobalExceptionHandler.java` la (`handleDuplicate` ku keezha) add:
```java
    @ExceptionHandler(InvalidDealOperationException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDeal(InvalidDealOperationException ex) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }
```
4. `dto/StageChangeRequest.java`:
```java
package com.pipelinecrm.dto;

import com.pipelinecrm.model.DealStage;
import jakarta.validation.constraints.NotNull;

public record StageChangeRequest(@NotNull(message = "stage is required") DealStage stage) {
}
```
5. `DealService.java` la imports add:
```java
import com.pipelinecrm.exception.InvalidDealOperationException;
import java.math.BigDecimal;
```
`update` method ah maathunga (closed check add):
```java
    @Transactional
    public DealResponse update(Long id, DealUpdateRequest request) {
        Deal deal = findDeal(id);
        if (deal.getStage().isClosed()) {
            throw new InvalidDealOperationException("Closed deal cannot be edited");
        }
        deal.setTitle(request.title());
        deal.setAmount(request.amount());
        return DealResponse.from(deal);
    }
```
Pudhu method add:
```java
    @Transactional
    public DealResponse changeStage(Long id, DealStage newStage) {
        Deal deal = findDeal(id);
        DealStage current = deal.getStage();

        if (!current.canMoveTo(newStage)) {
            throw new InvalidDealOperationException(
                    "Cannot move deal from " + current + " to " + newStage);
        }
        if (newStage == DealStage.WON && deal.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDealOperationException("A deal with amount 0 cannot be marked WON");
        }
        deal.setStage(newStage);
        return DealResponse.from(deal);
    }
```
6. `DealController.java` la import `com.pipelinecrm.dto.StageChangeRequest;` add pannitu:
```java
    @PatchMapping("/{id}/stage")
    public DealResponse changeStage(@PathVariable Long id, @Valid @RequestBody StageChangeRequest request) {
        return dealService.changeStage(id, request.stage());
    }
```
7. Stop > Run.

### Test
`requests.http` > **T7**: NEW deal ah QUALIFIED (200), NEW > WON (422), LOST aakkitu thirumba maatha (422), closed deal edit (422).

**Commit:** `PIPE-7: Enforce deal stage rules`

**Kathukittadhu:** Business rule thappu = 422. Rule ah oru edathula (enum) vaikkanum. Exception class vachu, `GlobalExceptionHandler` la HTTP code ku map pannanum.

---

## Ticket 8: Unit test (Mockito) for DealService

**Branch:** `feature/PIPE-8-dealservice-tests`

> **Manager:** "Stage rules la bug vandha prod la deals kedum. DealService.changeStage ku unit tests ezhudhunga. Happy path + ella rules um."

### Yen idhellam?
- **Unit test** = oru chinna piece (DealService) ah thani ah test panradhu. Database illa, web server illa, so 1 second ku kuraivaa mudiyum.
- **Integration test** = ellaa pieces um serndhu (controller + service + database) velai seiyudhaa nu paakkaradhu. Nermaiya, aana slow.
- **Mockito** = poli (fake) `DealRepository` ah create pannum. Neenga solluveenga: "`findById(1)` nu keta, indha deal ah kudu." Appo database thevai illa.

Test structure: **Given** (set up) > **When** (call pannu) > **Then** (check pannu).

### IntelliJ steps
`src/test/java/com/pipelinecrm/service/` package create pannitu `DealServiceTest.java`:
```java
package com.pipelinecrm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.pipelinecrm.dto.DealResponse;
import com.pipelinecrm.exception.InvalidDealOperationException;
import com.pipelinecrm.exception.ResourceNotFoundException;
import com.pipelinecrm.model.Contact;
import com.pipelinecrm.model.Deal;
import com.pipelinecrm.model.DealStage;
import com.pipelinecrm.repository.ContactRepository;
import com.pipelinecrm.repository.DealRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private DealService dealService;

    private Deal dealIn(DealStage stage, String amount) {
        Contact contact = new Contact();
        contact.setFirstName("Ravi");
        contact.setLastName("Kumar");

        Deal deal = new Deal();
        deal.setTitle("Test deal");
        deal.setAmount(new BigDecimal(amount));
        deal.setStage(stage);
        deal.setContact(contact);
        return deal;
    }

    @Test
    void movesToNextStage() {
        Deal deal = dealIn(DealStage.NEW, "1000");
        when(dealRepository.findById(1L)).thenReturn(Optional.of(deal));

        DealResponse response = dealService.changeStage(1L, DealStage.QUALIFIED);

        assertThat(response.stage()).isEqualTo(DealStage.QUALIFIED);
        assertThat(deal.getStage()).isEqualTo(DealStage.QUALIFIED);
    }

    @Test
    void cannotSkipStages() {
        Deal deal = dealIn(DealStage.NEW, "1000");
        when(dealRepository.findById(1L)).thenReturn(Optional.of(deal));

        assertThatThrownBy(() -> dealService.changeStage(1L, DealStage.WON))
                .isInstanceOf(InvalidDealOperationException.class)
                .hasMessageContaining("Cannot move deal from NEW to WON");
        assertThat(deal.getStage()).isEqualTo(DealStage.NEW);
    }

    @Test
    void canMoveToLostFromAnyOpenStage() {
        Deal deal = dealIn(DealStage.PROPOSAL, "1000");
        when(dealRepository.findById(1L)).thenReturn(Optional.of(deal));

        dealService.changeStage(1L, DealStage.LOST);

        assertThat(deal.getStage()).isEqualTo(DealStage.LOST);
    }

    @Test
    void closedDealCannotChange() {
        Deal deal = dealIn(DealStage.WON, "1000");
        when(dealRepository.findById(1L)).thenReturn(Optional.of(deal));

        assertThatThrownBy(() -> dealService.changeStage(1L, DealStage.LOST))
                .isInstanceOf(InvalidDealOperationException.class);
    }

    @Test
    void zeroAmountDealCannotBeWon() {
        Deal deal = dealIn(DealStage.NEGOTIATION, "0");
        when(dealRepository.findById(1L)).thenReturn(Optional.of(deal));

        assertThatThrownBy(() -> dealService.changeStage(1L, DealStage.WON))
                .isInstanceOf(InvalidDealOperationException.class)
                .hasMessageContaining("amount 0");
    }

    @Test
    void unknownDealThrowsNotFound() {
        when(dealRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dealService.changeStage(99L, DealStage.QUALIFIED))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
```
Class peru pakkathula green arrow > **Run 'DealServiceTest'**. 6 tests green ah irukkanum.

**Extra practice:** `Ctrl+Shift+T` pottu `DealService` ku test jump pannalaam. Oru test ah vendumne fail aaga (e.g. `QUALIFIED` ah `PROPOSAL` nu maathunga). Red aagudhu paarunga, appram sari pannunga. Test fail aagudhu nu paakkaradhum mukkiyam.

**Commit:** `PIPE-8: Add unit tests for deal stage rules`

**Kathukittadhu:** `@Mock` = poli, `@InjectMocks` = poli ah ulla vachu unmai class ah create pannu. Unit test = vegam, thani. Test peru ah paatha enna nadakkudhu nu puriyanum.

---

## Ticket 9: Data persist panna + Spring Profiles

**Branch:** `feature/PIPE-9-profiles-persistence`

> **Manager:** "App restart panna data poiduchu. Dev la file database la save aagattum (sample data um), prod la sample data venam, show-sql venam, H2 console venam. Tests ku thani setting."

### Yen idhellam?
- **H2 in-memory** = whiteboard la ezhudhura maari. App stop aana azhinjidum. **H2 file mode** = notebook la ezhudhura maari. Stop panninaalum irukkum. (Real company la H2 ku bathila PostgreSQL / MySQL use pannuvaanga, aana idea same.)
- **Profile** = "mode". Dev mode (neenga velai seiyum bodhu) la debug vishayam ellam on. Prod mode (customers use panra bodhu) la on pannina aabathu (H2 console la yaarum ulla poiduvaanga!). Same code, vera vera settings.
- `@Profile("dev")` = indha class dev mode la mattum run aagum.

### IntelliJ steps
1. `application.properties` ah muzhuvadhum maathunga:
```
spring.application.name=pipeline-crm
server.port=8080
spring.profiles.active=dev

spring.jpa.open-in-view=false
spring.data.web.pageable.max-page-size=50
```
2. `src/main/resources/application-dev.properties` (New > File):
```
spring.datasource.url=jdbc:h2:file:./data/pipelinecrm;AUTO_SERVER=TRUE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
3. `src/main/resources/application-prod.properties`:
```
spring.datasource.url=jdbc:h2:file:./data/pipelinecrm-prod;AUTO_SERVER=TRUE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=false

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

logging.level.root=WARN
logging.level.com.pipelinecrm=INFO
```
4. `src/test/resources/` folder create pannitu `application-test.properties`:
```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=false
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
```
5. `DataLoader.java` la class mela `@Component` ku keezha `@Profile("dev")` add pannunga (import: `org.springframework.context.annotation.Profile`):
```java
@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {
```
6. `PipelineCrmApplicationTests.java` ah maathunga (tests dev file DB ah thodaama irukka):
```java
package com.pipelinecrm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PipelineCrmApplicationTests {

    @Test
    void contextLoads() {
    }
}
```
7. Run pannunga. Project folder la `data` nu folder, `pipelinecrm.mv.db` file create aagirukkum (`.gitignore` la `data/` irukku, so Git ku pogaadhu).
8. **Persist check:** `POST /api/companies` (oru pudhu company), App stop > run > `GET /api/companies`. Pudhu company um irukkanum, seed data um **duplicate aagama** 3 companies mattum (DataLoader la `count() > 0` check irukku).
9. **Prod mode:** Stop pannunga. Top-right la run config peru click > **Edit Configurations** > `PipelineCrmApplication` select > **Modify options** > **Program arguments** add pannunga > idhai type pannunga: `--spring.profiles.active=prod` > OK > Run. Console la SQL kaattaadhu. `/h2-console` 404 varum, `/api/companies` la `[]` varum (sample data illa). Test mudinja appram program argument ah delete pannunga (dev ku thirumba).

**Commit:** `PIPE-9: Add dev/prod/test profiles and file-based H2`

**Kathukittadhu:** Settings ah code la hardcode pannaama profile files la vaikkanum. Secret / password ellam Git la poda koodaadhu (idhukku thaan real company la environment variables use pannuvaanga).

---

## Ticket 10: JAR build panni `java -jar` la run pannunga

**Branch:** `feature/PIPE-10-build-jar`

> **Manager:** "QA team ku build venum. Single JAR file, Java mattum irundha pothum, IntelliJ illaama run aaganum. Prod profile la run panni kaattunga."

### Yen idhellam?
Ippo neenga IntelliJ la green arrow click pannitu run panreenga. Customer server la IntelliJ irukkaadhu. Adhanaala **JAR** (Java ARchive = ella code + libraries um serntha oru zip maari file) create pannuvom. `spring-boot-maven-plugin` adhai **fat JAR** ah aakkum (tomcat server um ulla irukkum), so `java -jar` pannina pothum.

**Maven lifecycle** = padigal: `validate > compile > test > package`. `package` sonna, munnadi irukkura `compile` & `test` um thaane odum. **Test fail aana JAR create aagaadhu**. Adhu thaan nalladhu: odanja code QA ku pogaadhu.

### IntelliJ steps
1. Munnaadi run aagitu irukkura app ah **stop** pannunga (port 8080 busy aagum).
2. Right side la **Maven** tab (`m` icon) > `pipeline-crm` > **Lifecycle** > `clean` double-click, appram `package` double-click.
   (`clean` = pazhaya `target` folder ah azhi. `package` = tests odi JAR build.)
3. Run window la `BUILD SUCCESS` varanum. Tests: `Tests run: ... Failures: 0`.
4. `target` folder la `pipeline-crm-0.0.1-SNAPSHOT.jar` irukkum.
5. IntelliJ Terminal la:
```
java -jar target\pipeline-crm-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```
6. `requests.http` > **T10** la requests anuppunga. `[]` varum, companies create pannunga, Terminal la `Ctrl+C` pannitu thirumba run pannunga, data irukkum.
7. Tests skip panni quick build venum na (emergency la mattum): Maven tab la "Skip Tests" button (blue lightning icon) click pannitu `package` run pannunga. Idhu pazhakkam aaga koodaadhu, tests thaan namma paadhugaappu.
8. **Tag:** release ku peru:
```
git tag v0.1.0
git push origin v0.1.0
```
JAR ah Git la podaadheenga (`target/` ignored). Binary files ku Git sari illa.

**Commit:** Indha ticket la code maatram illa na, commit thevai illa. README la "How to build" 3 line ezhudhi commit pannunga: `PIPE-10: Document build and run steps`

**Kathukittadhu:** `mvn clean package` = build. Fat JAR = ellam ulla. Test fail na build fail (idhu protection). `--spring.profiles.active=prod` = JAR ah thirumba build pannaama mode maathalaam.

---

## Sprint 1 mudinjuchu!

Neenga ippo: field add, validation, pudhu feature, aggregate query, pagination, CRUD, business rules (422), unit tests, profiles, JAR build, ellam Git + PR workflow la pannirukeenga. Real company la junior ku varra velai idhu thaan.

**Sprint 2 (Tickets 11-20)** next: Git la merge conflict, existing code la bug fix, SQL joins, search, DTO mapping, logging, global error handling, integration tests, security basics, API documentation.
