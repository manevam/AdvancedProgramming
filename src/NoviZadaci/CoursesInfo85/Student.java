package NoviZadaci.CoursesInfo85;

public class Student {
    int id;
    String name;
    int prvKolPoeni;
    int vtorKolPoeni;
    int labsPoeni;

    public Student(String index, String name) {
        this.id = Integer.parseInt(index);
        this.name = name;
        this.prvKolPoeni = 0;
        this.vtorKolPoeni = 0;
        this.labsPoeni = 0;
    }

    public void setPrvKolPoeni(int prvKolPoeni) {
        this.prvKolPoeni = prvKolPoeni;
    }

    public void setVtorKolPoeni(int vtorKolPoeni) {
        this.vtorKolPoeni = vtorKolPoeni;
    }

    public void setLabsPoeni(int labsPoeni) {
        this.labsPoeni = labsPoeni;
    }

    public double sumarniPoeni(){
        return prvKolPoeni * 0.45 + vtorKolPoeni * 0.45 + labsPoeni;
    }
    public int getGrade(){
        double points = sumarniPoeni();
        if (points>50 && points<=60)
            return 6;
        if(points>60 && points<=70)
            return 7;
        if(points>70 && points<=80)
            return 8;
        if(points>80 && points<=90)
            return 9;
        if(points>90 && points<=100)
            return 10;

        return 5;
    }

    @Override
    public String toString() {
        return String.format("ID: %d Name: %s First midterm: %d Second midterm: %d Labs: %d Summary points: %.2f Grade: %d",
                id, name, prvKolPoeni, vtorKolPoeni, labsPoeni, sumarniPoeni(), getGrade());
    }
}
