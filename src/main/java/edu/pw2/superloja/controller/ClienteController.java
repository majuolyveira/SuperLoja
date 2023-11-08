package edu.pw2.superloja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pw2.superloja.model.cliente.Cliente;
import edu.pw2.superloja.model.cliente.ClienteData;
import edu.pw2.superloja.model.cliente.ClienteDataResumo;
import edu.pw2.superloja.model.cliente.ClienteRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepo;

    @GetMapping
    public Page<ClienteData> getAllClientes(@PageableDefault(sort = {"nome"}, size = 10) Pageable paginacao){
        return clienteRepo.findAll(paginacao).map(ClienteData::new);
    }

    @GetMapping("/{id}")
    public ClienteData getCliente(@PathVariable Long id, HttpServletResponse res){
        if(id != null && clienteRepo.existsById(id)){
            Cliente c = clienteRepo.getReferenceById(id);
            ClienteData cd = new ClienteData(c);
            return cd;
        }
        res.setStatus(204);
        return null;
    }

    @PostMapping
    @Transactional
    public void saveCliente(@RequestBody ClienteData dados){
        Cliente c = new Cliente(dados);
        clienteRepo.save(c);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteCliente(@PathVariable Long id){
        if(id != null){
            clienteRepo.deleteById(id);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public void updateCliente(@PathVariable Long id, @RequestBody ClienteDataResumo data){
         if(id != null){
            Cliente c = clienteRepo.getReferenceById(id);
            c.update(data);
        }
    }
}
