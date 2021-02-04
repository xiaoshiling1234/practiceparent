package models.structural.composite;

public class Client {
    public static void main(String[] args) {
        //创建根节点
        Composite root = new Composite();
        root.operation();
        //创建树枝节点
        Composite branch = new Composite();
        //创建叶子节点
        Leaf leaf = new Leaf();
        //构建树形结构
        root.add(branch);
        branch.add(leaf);
        display(root);
    }

    public static void display(Composite root) {
        for (Component c : root.getChild()) {
            if (c instanceof Leaf) {
                c.operation();
            } else {
                c.operation();
                display((Composite) c);
            }
        }
    }
}
