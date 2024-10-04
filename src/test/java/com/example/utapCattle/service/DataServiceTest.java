package com.example.utapCattle.service;

import com.example.utapCattle.mapper.AgentMapper;
import com.example.utapCattle.mapper.BreedMapper;
import com.example.utapCattle.mapper.CategoryMapper;
import com.example.utapCattle.mapper.CustomerMapper;
import com.example.utapCattle.mapper.FarmMapper;
import com.example.utapCattle.mapper.MarketMapper;
import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.dto.AllDataDto;
import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.model.dto.CategoryDto;
import com.example.utapCattle.model.dto.CustomerDto;
import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Agent;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.model.entity.Category;
import com.example.utapCattle.model.entity.Customer;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.service.impl.DataServiceImpl;
import com.example.utapCattle.service.repository.AgentRepository;
import com.example.utapCattle.service.repository.BreedRepository;
import com.example.utapCattle.service.repository.CategoryRepository;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.CustomerRepository;
import com.example.utapCattle.service.repository.DefaultTreatmentRepository;
import com.example.utapCattle.service.repository.FarmRepository;
import com.example.utapCattle.service.repository.MarketRepository;
import com.example.utapCattle.service.repository.MedicalConditionRepository;
import com.example.utapCattle.service.repository.MedicationRepository;
import com.example.utapCattle.service.repository.PenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DataServiceTest {

    @Mock
    private FarmRepository farmRepository;
    @Mock
    private BreedRepository breedRepository;
    @Mock
    private MarketRepository marketRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AgentRepository agentRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private MedicalConditionRepository medicalConditionRepository;
    @Mock
    private MedicationRepository medicationRepository;
    @Mock
    private CattleRepository cattleRepository;
    @Mock
    private PenRepository penRepository;
    @Mock
    private DefaultTreatmentRepository defaultTreatmentRepository;

    private FarmMapper farmMapper = Mappers.getMapper(FarmMapper.class);
    private BreedMapper breedMapper = Mappers.getMapper(BreedMapper.class);
    private MarketMapper marketMapper = Mappers.getMapper(MarketMapper.class);
    private CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private AgentMapper agentMapper = Mappers.getMapper(AgentMapper.class);
    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);
    private DataService service ;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new DataServiceImpl(farmRepository
                , breedRepository
                , marketRepository
                , categoryRepository
                , agentRepository
                , customerRepository
                , medicalConditionRepository
                , medicationRepository
                , cattleRepository
                , penRepository
                , defaultTreatmentRepository);
    }

    @Test
    public void testGetAllData_WhenDataExists_ShouldReturnAllData() {
        final List<Farm> farms = Arrays.asList(new FarmDto().builder().build()
                        , new FarmDto().builder().build())
                .stream().map(farmMapper::toEntity).collect(Collectors.toList());

        final List<Breed> breeds = Arrays.asList(new BreedDto().builder().build()
                        , new BreedDto().builder().build())
                .stream().map(breedMapper::toEntity).collect(Collectors.toList());

        final List<Market> markets = Arrays.asList(new MarketDto().builder().build()
                        , new MarketDto().builder().build())
                .stream().map(marketMapper::toEntity).collect(Collectors.toList());

        final List<Category> categories = Arrays.asList(new CategoryDto().builder().build()
                        , new CategoryDto().builder().build())
                .stream().map(categoryMapper::toEntity).collect(Collectors.toList());

        final List<Agent> agents = Arrays.asList(new AgentDto().builder().build()
                        , new AgentDto().builder().build())
                .stream().map(agentMapper::toEntity).collect(Collectors.toList());

        final List<Customer> customers = Arrays.asList(new CustomerDto().builder().build()
                        , new CustomerDto().builder().build())
                .stream().map(customerMapper::toEntity).collect(Collectors.toList());

        AllDataDto dto = new AllDataDto().builder()
                .sourceFarm(farms)
                .breed(breeds)
                .market(markets)
                .category(categories)
                .agent(agents)
                .fatteningFor(customers)
                .build();

        when(farmRepository.findAll()).thenReturn(farms);
        when(breedRepository.findAll()).thenReturn(breeds);
        when(marketRepository.findAll()).thenReturn(markets);
        when(categoryRepository.findAll()).thenReturn(categories);
        when(agentRepository.findAll()).thenReturn(agents);
        when(customerRepository.findAll()).thenReturn(customers);

        AllDataDto result = service.getAllData();
        assertEquals(result, dto);
    }

    //TODO: still have to write few more test cases
}
