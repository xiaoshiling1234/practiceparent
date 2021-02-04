package models.create.buider.old;

//装机人员
public class Director {
    private ComputerConfigBuilder mBuilder;

    public Director(ComputerConfigBuilder mBuilder) {
        this.mBuilder = mBuilder;
    }

    public void setmBuilder(ComputerConfigBuilder mBuilder) {
        this.mBuilder = mBuilder;
    }

    public void createComputer() {
        mBuilder.setCPU();
        mBuilder.setMemery();
        mBuilder.setHardDisk();
        mBuilder.setKeyboard();
        mBuilder.setMouse();
    }

    public Computer getComputer() {
        return mBuilder.getComputer();
    }

    ;
}
