package NoviZadaci.CoursesInfo85;

import java.util.*;
import java.util.stream.Collectors;

public class AdvancedProgrammingCourse {

    List <Student> students;

    public AdvancedProgrammingCourse() {
        this.students = new ArrayList<Student>();
    }

    public void addStudent(Student s){
        students.add(s);
    }

    public void updateStudent(String idNumber, String activity, int points){
        students.stream().filter(s -> s.id == Integer.parseInt(idNumber))
                .forEach(s -> {
                    if(activity.equals("midterm1"))
                        s.setPrvKolPoeni(points);
                    if(activity.equals("midterm2"))
                        s.setVtorKolPoeni(points);
                    if(activity.equals("labs"))
                        s.setLabsPoeni(points);
                });
    }

    public List<Student> getFirstNStudents(int n){
        return students.stream().sorted(Comparator.comparing(Student::sumarniPoeni).reversed())
                .limit(n).collect(Collectors.toList());
    }

    public Map<Integer, Integer> getGradeDistribution(){
        Map<Integer, Integer> grades = new HashMap<Integer,Integer>();
        grades.put(5,0);
        grades.put(6,0);
        grades.put(7,0);
        grades.put(8,0);
        grades.put(9,0);
        grades.put(10,0);

        students.stream().map(s -> s.getGrade()).forEach(grade -> {
            grades.computeIfPresent(grade, (key, value) -> value + 1);
        });

        return grades;
    }

    public void printStatistics(){
        DoubleSummaryStatistics dss = students.stream()
                .filter(s -> s.getGrade() >=6)
                .mapToDouble(s -> s.sumarniPoeni())
                .summaryStatistics();

        System.out.println(String.format("Count: %d Min: %.2f Average: %.2f Max: %.2f",
                dss.getCount(), dss.getMin(), dss.getAverage(), dss.getMax()));
    }
}
