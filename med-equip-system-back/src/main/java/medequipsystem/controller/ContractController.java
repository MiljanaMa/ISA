package medequipsystem.controller;

import medequipsystem.domain.CompanyAdmin;
import medequipsystem.domain.Contract;
import medequipsystem.dto.ContractDTO;
import medequipsystem.dto.FullContractDTO;
import medequipsystem.dto.ReservationDTO;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.CompanyAdminService;
import medequipsystem.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/contracts")
public class ContractController {
    @Autowired
    private CompanyAdminService adminService;

    @Autowired
    private ContractService contractService;

    @GetMapping(value = "/get")
    @PreAuthorize("hasAnyRole('COMPADMIN')")
    public ResponseEntity<Set<FullContractDTO>> getCompanyContracts(Principal user) {
        try{
            CompanyAdmin admin = adminService.getLogged(user);
            Set<Contract> contracts = contractService.getByCompany(admin.getCompany().getId());
            Set<FullContractDTO> contractDTOS = (Set<FullContractDTO>) new DtoUtils().convertToDtos(contracts, new FullContractDTO());
            return new ResponseEntity<>(contractDTOS, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "/cancel")
    @PreAuthorize("hasAnyRole('COMPADMIN')")
    public ResponseEntity<FullContractDTO> cancelContract(@RequestBody ContractDTO cancelledContract,Principal user) {
        try{
            CompanyAdmin admin = adminService.getLogged(user);
            Contract contract = contractService.cancelContract(cancelledContract.getId());
            FullContractDTO contractDTO = (FullContractDTO) new DtoUtils().convertToDto(contract, new FullContractDTO());
            return new ResponseEntity<>(contractDTO, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
