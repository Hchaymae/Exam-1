package org.example.exam.beans;


import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.example.exam.ejb.CDService;
import org.example.exam.entity.CD;

import java.util.List;

@Named
@RequestScoped
public class CDBean {
    @EJB
    private CDService cdService;

    private CD cd = new CD();

    public void addCD() {
        cdService.addCD(cd);
        cd = new CD(); 
    }

    public List<CD> getAllCDs() {
        return cdService.getAllCDs();
    }

    public CD getCd() {
        return cd;
    }

    public void setCd(CD cd) {
        this.cd = cd;
    }
}
