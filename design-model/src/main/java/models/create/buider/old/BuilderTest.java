package models.create.buider.old;

public class BuilderTest {
    public static void main(String[] args) {
        HighConfigBuider highConfigBuider = new HighConfigBuider();
        Director director = new Director(highConfigBuider);
        director.createComputer();
        System.out.println(director.getComputer());

        LowConfigBuilder lowConfigBuilder = new LowConfigBuilder();
        director.setmBuilder(lowConfigBuilder);
        director.createComputer();
        director.getComputer();
        System.out.println(director.getComputer());

        Person person = new Person.Builder("张三", "男")
                .age("12")
                .money("10000")
                .car("宝马")
                .build();
        System.out.println(person);
    }
}
