package 树;

import 栈.SqStack;
import 队列.LkQueue;

public class BinaryTree {

    //先序遍历
    void preOrder(BitNode bitNode){
        if (bitNode!=null){
            visit(bitNode);
            preOrder(bitNode.lChild);
            preOrder(bitNode.rChild);
        }
    }

    //中序遍历
    void inOrder(BitNode bitNode){
        if (bitNode!=null){
            preOrder(bitNode.lChild);
            visit(bitNode);
            preOrder(bitNode.rChild);
        }
    }

    //后序遍历
    void postOrder(BitNode bitNode){
        if (bitNode!=null){
            preOrder(bitNode.lChild);
            preOrder(bitNode.rChild);
            visit(bitNode);
        }
    }

    //求深度
    int treeDepth(BitNode bitNode){
        if (bitNode==null){
            return 0;
        }
        int l=treeDepth(bitNode.lChild);
        int r=treeDepth(bitNode.rChild);
        return l>r?l+1:r+1;
    }

    //层次遍历 借助队列
    void levelOrder(BitNode bitNode){
        LkQueue lkQueue = new LkQueue<BitNode>();
        BitNode<BitNode> p;
        lkQueue.enQueue(bitNode);
        while (!lkQueue.queueEmpty()){
            p=(BitNode)lkQueue.deQueue();
            visit(p);
            if (p.lChild!=null){
                lkQueue.enQueue(p.lChild);
            }
            if (p.rChild!=null){
                lkQueue.enQueue(p.rChild);
            }
        }
    }

    public void visit(BitNode bitNode){
        System.out.println(bitNode.data);
    }
}
