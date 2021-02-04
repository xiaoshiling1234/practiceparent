package models.create.buider.old;

public class LowConfigBuilder implements ComputerConfigBuilder {
    private Computer mComputer;

    public LowConfigBuilder() {
        this.mComputer=new Computer();
    }

    public void setCPU() {
        mComputer.setCPU("i5");
    }

    public void setMemery() {
        mComputer.setMemory("8G");
    }

    public void setHardDisk() {
        mComputer.setHardDisk("500G");
    }

    public void setKeyboard() {
        mComputer.setKeyboard("薄膜键盘");
    }

    public void setMouse() {
        mComputer.setMouse("无线鼠标");
    }

    public Computer getComputer() {
        return mComputer;
    }
}
