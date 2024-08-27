// src/test/java/com/revature/LLL/LivestockRecord/LivestockRecordControllerTestSuite.java
package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.LLL.User.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LivestockRecordControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivestockRecordService livestockRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test the getLivestockRecordByEntryId method in LivestockRecordController
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordByEntryId() throws Exception {
        // Arrange
        int entryId1 = 1;
        int entryId2 = 2;

        LivestockRecord record1 = new LivestockRecord();
        record1.setEntryId(entryId1);

        LivestockRecord record2 = new LivestockRecord();
        record2.setEntryId(entryId2);

        when(livestockRecordService.findById(entryId1)).thenReturn(record1);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/entry")
                        .param("entryId", String.valueOf(entryId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record1)));
    }

    /**
     * Test the getLivestockRecordsByUserId method in LivestockRecordController
     * User type is VET
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordsByUserIdAsVet() throws Exception {
        // Arrange
        int userId = 1;
        String userType = "VET";

        User vet = new User();
        vet.setUserId(userId);

        VetRecord vetRecord = new VetRecord();
        vetRecord.setVetDetails(vet);

        LivestockRecord record1 = new LivestockRecord();
        record1.setVetRecord(vetRecord);

        LivestockRecord record2 = new LivestockRecord();
        record2.setVetRecord(vetRecord);

        List<LivestockRecord> records = Arrays.asList(record1, record2);

        when(livestockRecordService.findAllByVetRecordVetDetailsUserId(userId)).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/user")
                        .param("userId", String.valueOf(userId))
                        .header("userType", userType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    /**
     * Test the getLivestockRecordsByUserId method in LivestockRecordController
     * User type is OWNER
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordsByUserIdAsOwner() throws Exception {
        // Arrange
        int userId = 1;
        String userType = "OWNER";

        User owner = new User();
        owner.setUserId(userId);

        PatientIdentification patientIdentity = new PatientIdentification();
        patientIdentity.setOwnerInfo(owner);

        LivestockRecord record1 = new LivestockRecord();
        record1.setPatientIdentification(patientIdentity);

        LivestockRecord record2 = new LivestockRecord();
        record2.setPatientIdentification(patientIdentity);

        List<LivestockRecord> records = Arrays.asList(record1, record2);

        when(livestockRecordService.findAllByPatientIdentificationOwnerInfoUserId(userId)).thenReturn(records);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/user")
                        .param("userId", String.valueOf(userId))
                        .header("userType", userType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(records)));
    }

    /**
     * Test the getLivestockRecordByAnimalId method in LivestockRecordController
     * @throws Exception
     */
    @Test
    public void testGetLivestockRecordByAnimalId() throws Exception {
        // Arrange
        int animalId = 1;

        PatientIdentification patientIdentity = new PatientIdentification();
        patientIdentity.setAnimalId(animalId);

        LivestockRecord record = new LivestockRecord();
        record.setPatientIdentification(patientIdentity);

        when(livestockRecordService.findByPatientIdentificationAnimalId(animalId)).thenReturn(record);

        // Act & Assert
        mockMvc.perform(get("/medicalRecord/animal")
                        .param("animalId", String.valueOf(animalId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record)));
    }





    @Test
    public void testFindByAnimalId() throws Exception{
        int animalId = 1;
        PatientIdentification patientIdentification = new PatientIdentification();
        patientIdentification.setAnimal_id(animalId);
        LivestockRecord record = new LivestockRecord();
        record.setPatientIdentification(patientIdentification);

        when(livestockRecordService.findByAnimalId(animalId)).thenReturn(record);
        mockMvc.perform(get("/medicalRecord/animal")
                        .param("animalId", String.valueOf(animalId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record)));
        verify(livestockRecordService, times(1)).findByAnimalId(animalId);
    }

    @Test
    public void testUpdateSymptoms() throws Exception {
        // Arrange
        String[] newSymptoms = new String[]{"symptom3", "symptom4"};

        CurrentCondition condition = new CurrentCondition();
        condition.setSymptoms(newSymptoms);

        LivestockRecord record1 = new LivestockRecord();
        record1.setCondition(condition);
        record1.setPatientIdentification(new PatientIdentification());
        record1.getPatientIdentification().setAnimal_id(1);

        when(livestockRecordService.findByAnimalId(1)).thenReturn(record1);
        when(livestockRecordService.updateSymptoms(eq(record1))).thenReturn(record1);

        // Act & Assert
        mockMvc.perform(patch("/medicalRecord/symptoms")
                        .param("animalId", String.valueOf(1))
                        .content(objectMapper.writeValueAsString(newSymptoms))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record1)));
        verify(livestockRecordService, times(1)).findByAnimalId(1);
        verify(livestockRecordService, times(1)).updateSymptoms(record1);
    }
    @Test
    public void testUpdateMedicalHistory() throws Exception{
        // set up MedicalHistory and TreatmentPlan objects to use during the patch
        MedicalHistory medicalHistory = new MedicalHistory();
        TreatmentPlan treatmentPlan = new TreatmentPlan();
        treatmentPlan.setAntibiotics(new String[]{"antibiotic1", "antibiotic2"});
        treatmentPlan.setMedications_prescribed(new String[]{"medication1", "medication2"});
        treatmentPlan.setTreatment_procedures("procedure1");
        treatmentPlan.setFollowup_instructions("instructions1");

        medicalHistory.setPrevious_treatments(new TreatmentPlan[]{treatmentPlan});
        medicalHistory.setPrevious_illnesses(new String[]{"illness1", "illness2"});
        medicalHistory.setVaccination_history(new String[]{"vaccination1", "vaccination2"});

        // set up PatientIdentification object to be used to find which record to update via animal_id
        PatientIdentification mockPatientIdentification = new PatientIdentification();
        mockPatientIdentification.setAnimal_id(1);
        mockPatientIdentification.setBreed("dog");
        mockPatientIdentification.setAge(5);
        mockPatientIdentification.setSex(PatientIdentification.Sex.MALE);
        mockPatientIdentification.setOwnerInfo(new User(1, "John", "Doe", "john@mail.com", "johnpw", User.userType.VET));



        LivestockRecord record1 = new LivestockRecord();
        record1.setMedicalHistory(medicalHistory);
        record1.setPatientIdentification(mockPatientIdentification);

        // return the record1 which has animal_id = 1, needed because the patch request needs to find the record to update
        when(livestockRecordService.findByAnimalId(1)).thenReturn(record1);

        // return the record1 after updating the medical history
        when(livestockRecordService.updateMedicalHistory(record1)).thenReturn(record1);

        mockMvc.perform(patch("/medicalRecord/medicalHistory")
                        .param("animalId", String.valueOf(1))
                        .header("userType", "VET")
                        .content(objectMapper.writeValueAsString(record1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(record1)));   // expects record1 to be returned after updating the medical history

        verify(livestockRecordService, times(1)).updateMedicalHistory(record1);
    }
}