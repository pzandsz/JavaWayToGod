package algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 类说明:克隆图
 * 深度优先搜索DFS
 *
 * 1.从给定节点开始遍历图
 * 2.使用一个HashMap存储所有已经被访问过的和复制的节点，hashMap中key是原始图中节点,
 * value是克隆图中对应的节点，如果某个节点已经被访问过，则返回其克隆图中的对应节点
 *
 * 给定A-B,表示A能连接到B且B能连接到A,如果对访问过的节点不做标记，则会陷入死循环中。
 *
 * 3.如果当访问的节点不在HashMap中，则创建它的克隆节点存储在HashMap中，注意：进入递归之前，必须先创建
 * 克隆节点并保存在HashMap
 *
 * 4.递归调用每个节点的邻接点。每个节点递归调用的次数等于邻接点的数量，
 * 每一次调用返回其对应邻接点的克隆节点，最终返回这些克隆邻接点的列表，
 * 将其放入对应克隆节点的邻接表中。这样就可以克隆给定的节点和其邻接点。
 *
 *
 * @author 曾鹏
 */
public class CloneGraph {

    private HashMap<Node,Node> nodeHashMap = new HashMap<Node, Node>();
    public Node cloneGraph(Node node) {
        if(node == null){
            return node;
        }

        if(nodeHashMap.containsKey(node)){
            return nodeHashMap.get(node);
        }

        Node nodeCopy = new Node(node.val,new ArrayList<Node>());
        nodeHashMap.put(node,nodeCopy);

        for(Node tmp : node.neighbors){
            nodeCopy.neighbors.add(cloneGraph(tmp));
        }
        return nodeCopy;
    }





    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
