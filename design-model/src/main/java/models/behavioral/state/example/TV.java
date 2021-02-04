package models.behavioral.state.example;

public class TV {
    public final static Channel CCTV1=new CCTV1();
    public final static Channel CCTV2=new CCTV2();
    public final static Channel CCTV3=new CCTV3();

    //当前频道
    private Channel channel;

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    //播放
    public void dsCCTV1(){
        setChannel(CCTV1);
        CCTV1.display();
    }
    public void dsCCTV2(){
        setChannel(CCTV2);
        CCTV2.display();
    }
    public void dsCCTV3(){
        setChannel(CCTV3);
        CCTV3.display();
    }
}
