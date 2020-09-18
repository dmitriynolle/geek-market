package XLS;

import java.util.ArrayList;
import java.util.List;

public class Company {
    String parrent;
    String company;
    double prognoz;
    double fact;
    double percent;
    List<Company> subsidiary = new ArrayList<>();

    public Company() {
    }

    public void setParrent(String parrent) {
        this.parrent = parrent;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getPrognoz() {
        return prognoz;
    }

    public void setPrognoz(double prognoz) {
        this.prognoz = prognoz;
    }

    public double getFact() {
        return fact;
    }

    public void setFact(double fact) {
        this.fact = fact;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setSubsidiary(Company subsidiary) {
        this.subsidiary.add(subsidiary);
    }
}
