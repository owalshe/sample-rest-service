package rest.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import rest.service.model.Address;
import rest.service.model.Person;
import rest.service.repository.PersonRepository;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ResourceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PersonRepository repository;
	
	private Long nextId = new Long(3);
	
	private Person existingPerson;
	
    @BeforeEach
    public void setup () {
    	Address address = new Address("street1", "city1", "state1", "postalCode1");
    	this.existingPerson = this.repository.save(new Person("data1", "data2", Collections.singletonList(address)));
    }
	
	@Test
	public void testGet() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get(String.format("/person/%d", this.existingPerson.getId())).accept(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk())
	        .andExpect(content().string(new ObjectMapper().writeValueAsString(this.existingPerson)));
	}
	
	@Test
	public void testGetAll() throws Exception{
		List<Person> refList = Collections.singletonList(this.existingPerson);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/person").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(refList)));
	}
	
	@Test
	public void testCount() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get("/person/count").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(new Long(1))));
	}
	
	@Test
	public void testPost() throws Exception{
		Person ref = new Person("data4", "data5");
		ref.setId(this.nextId);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(
				ref)))
            .andExpect(status().isCreated())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(ref)));
	}
	
	@Test
	public void testPostAddress() throws Exception{
		Address ref = new Address("Street1", "City1", "State1", "PostalCode1");
		ref.setId(this.nextId);
		this.mockMvc.perform(MockMvcRequestBuilders.post(String.format("/address/%d", existingPerson.getId())).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(
				ref)))
            .andExpect(status().isCreated())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(ref)));
	}
	
	@Test
	public void testPut() throws Exception{
		Person ref = new Person("data3", "data4", this.existingPerson.getAddresses());
		ref.setId(this.existingPerson.getId());
		this.mockMvc.perform(MockMvcRequestBuilders.put(String.format("/person/%d", existingPerson.getId())).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(ref)))
            .andExpect(status().isOk())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(ref)));
	}
	
	@Test
	public void testPutAddress() throws Exception{
		Long existingAddressId = this.existingPerson.getAddresses().get(0).getId();
		Address ref = new Address("Street2", "City2", "State2", "PostalCode2");
		ref.setId(existingAddressId);
		this.mockMvc.perform(MockMvcRequestBuilders.put(String.format("/address/%d", existingAddressId)).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(ref)))
            .andExpect(status().isOk())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(ref)));
	}
	
	@Test
	public void testDelete() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/person/%d", this.existingPerson.getId())))
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));
	}
	
	@Test
	public void testDeleteAddress() throws Exception{
		Long existingAddressId = this.existingPerson.getAddresses().get(0).getId();
		this.mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/address/%d", existingAddressId)))
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));
	}
	
	@Test
	public void testGetResourceNotFound() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get("/person/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
	}
	
	@Test
	public void testDeletResourceNotFound() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
	}
	
	@Test
	public void testResourceAlreadyExists() throws Exception{
		Person ref = new Person("data1", "data2");
		this.mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(ref)))
        .andExpect(status().isConflict());
	}
}
