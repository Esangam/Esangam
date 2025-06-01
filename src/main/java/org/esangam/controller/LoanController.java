package org.esangam.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.esangam.entity.Loan;
import org.esangam.service.LoanService;


import java.util.List;

@Path("/esangam/loan")
public class LoanController {

    LoanService loanService;

    @Inject
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @POST
    public void addLoan(Loan loan){
        loanService.addLoan(loan);
    }

    @GET
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

}
