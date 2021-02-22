package com.rolingvistica.backend.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.rolingvistica.backend.model.*;
import com.rolingvistica.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@ConditionalOnProperty(name = "transcribe", havingValue = "true")
public class TranscribeCSV implements CommandLineRunner {
    final private AnswerRepository answerRepository;
    final private ContestRepository contestRepository;
    final private PartialScorePerElementRepository partialScorePerElementRepository;
    final private ProblemRepository problemRepository;
    final private RegistrationRepository registrationRepository;
    final private RequirementRepository requirementRepository;
    final private RoleRepository roleRepository;
    final private ScoreRepository scoreRepository;
    final private SectionRepository sectionRepository;
    final private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(TranscribeCSV.class);

    private static final String CONTEST = "Concursul Online de Lingvistica 2020";
    private static final String PROBLEM = "Problema 2 - japoneza";
    private static final String SECTION1 = "Elev de gimnaziu (5-8)";
    private static final String SECTION2 = "Elev de liceu (9-12)";

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Incepem");
        logger.info("Incepem");
        Contest contest = new Contest();
        contest.setName(CONTEST);
        contest = contestRepository.save(contest);

        Section s1 = new Section();
        s1.setName(SECTION1);
        s1 = sectionRepository.save(s1);

        Section s2 = new Section();
        s2.setName(SECTION2);
        s2 = sectionRepository.save(s2);

        Problem problem = new Problem();
        problem.setContest(contest);
        problem.setSection(s1);
        problem.setName(PROBLEM);
        problem = problemRepository.save(problem);

        Problem problem1 = new Problem();
        problem1.setContest(contest);
        problem1.setSection(s2);
        problem1.setName(PROBLEM);
        problem1 = problemRepository.save(problem1);

        Role role = new Role();
        role.setName("contestant");
        role = roleRepository.save(role);

        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
            CSVReader reader = new CSVReaderBuilder(new FileReader("D:\\work\\free_time_projects\\rolingvistica\\backend\\src\\main\\resources\\data_files\\COL2020-Problema-2-Responses-Form-Responses-2.csv"))
                    .withCSVParser(csvParser)
                    .build();
            List<String[]> records = reader.readAll();
            String[] headers = records.get(0);
            records.remove(0);
            List<Requirement> requirements1 = new ArrayList<>();
            List<Requirement> requirements2 = new ArrayList<>();
            for(int i = 8 ; i < headers.length; i++)
            {
                Requirement requirement = new Requirement();
                requirement.setCorrectAnswer("");
                requirement.setProblem(problem);
                requirement.setSpecification(headers[i]);
                requirement = requirementRepository.save(requirement);
                requirements1.add(requirement);


                Requirement requirement1 = new Requirement();
                requirement1.setCorrectAnswer("");
                requirement1.setProblem(problem1);
                requirement1.setSpecification(headers[i]);
                requirement1 = requirementRepository.save(requirement1);
                requirements2.add(requirement1);
            }
            for(String[] record: records){
                //handle user
                User user = new User();
                Optional<User> optionalUser = userRepository.findByUsername(record[3]);
                if( optionalUser.isPresent()){
                    user = optionalUser.get();
                }
                else{
                    user.setEmail(record[1]);
                    user.setFullName(record[3]);
                    user.setRole(role);
                    user.setUsername(record[3]);
                    user.setPassword(record[3]);
                    user = userRepository.save(user);
                }

                //handle
                String section = record[4];
                //System.out.println(section);
                //System.out.println(s1.getName());
                //System.out.println(s2.getName());
                //System.out.println(section.equals(s1.getName()));
                Section usedSection = null;
                List<Requirement> usedRequirements = null;
                if( section.equals(s1.getName())){
                    usedSection = s1;
                    usedRequirements = requirements1;
                }
                else if (section.equals(s2.getName())) {
                    usedSection = s2;
                    usedRequirements = requirements2;
                } else {
                    continue;
                }
                //create registration
                Registration registration = new Registration();
                registration.setContest(contest);
                registration.setUser(user);
                registration.setSection(usedSection);
                registration = registrationRepository.save(registration);

                for(int i = 8 ; i < headers.length; i++){
                    Answer answer = new Answer();
                    answer.setProvidedAnswer( record[i]);
                    answer.setRequirement(usedRequirements.get(i-8));
                    answer.setRegistration(registration);
                    answer = answerRepository.save(answer);
                }
            }
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Terminam");
        logger.info("Terminam");
    }



}
