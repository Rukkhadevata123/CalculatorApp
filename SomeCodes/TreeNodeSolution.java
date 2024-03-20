import java.util.*;
import javax.swing.*;
import java.awt.*;

class TreeNode {
    String val;
    TreeNode left;
    TreeNode right;

    TreeNode(String x) {
        val = x;
    }
}

class TreePanel extends JPanel {
    TreeNode root;

    public TreePanel(TreeNode root) {
        this.root = root;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int treeHeight = calculateTreeHeight(root);
        int xInc = getWidth() / (int) Math.pow(2, treeHeight + 1);
        drawTree(g, getWidth() / 2, 40, xInc, root);
    }

    private int calculateTreeHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calculateTreeHeight(node.left), calculateTreeHeight(node.right));
    }

    private void drawTree(Graphics g, int x, int y, int xInc, TreeNode node) {
        if (node == null) {
            return;
        }

        g.drawOval(x, y, 30, 30);
        g.drawString(node.val, x + 10, y + 20);

        if (node.left != null) {
            g.drawLine(x + 15, y + 30, x - xInc + 15, y + 60);
            drawTree(g, x - xInc, y + 60, xInc / 2, node.left);
        }

        if (node.right != null) {
            g.drawLine(x + 15, y + 30, x + xInc + 15, y + 60);
            drawTree(g, x + xInc, y + 60, xInc / 2, node.right);
        }
    }
}

public class TreeNodeSolution {
    private static final int MAX_LENGTH = 1000;

    public TreeNode buildTree(String[] preorder, String[] inorder) {
        if (preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        String rootValue = preorder[0];
        TreeNode root = new TreeNode(rootValue);

        int rootIndex = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i].equals(rootValue)) {
                rootIndex = i;
                break;
            }
        }

        String[] leftInorder = Arrays.copyOfRange(inorder, 0, rootIndex);
        String[] rightInorder = Arrays.copyOfRange(inorder, rootIndex + 1, inorder.length);
        String[] leftPreorder = Arrays.copyOfRange(preorder, 1, leftInorder.length + 1);
        String[] rightPreorder = Arrays.copyOfRange(preorder, leftInorder.length + 1, preorder.length);

        root.left = buildTree(leftPreorder, leftInorder);
        root.right = buildTree(rightPreorder, rightInorder);

        return root;
    }

    public void postorderTraversal(TreeNode root) {
        if (root != null) {
            postorderTraversal(root.left);
            postorderTraversal(root.right);
            System.out.print(root.val + " ");
        }
    }

    public boolean checkSameType(String[] array) {
        boolean isDigit = array[0].matches("\\d+");
        for (String s : array) {
            if (isDigit != s.matches("\\d+")) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSameElements(String[] array1, String[] array2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(array1));
        Set<String> set2 = new HashSet<>(Arrays.asList(array2));
        return set1.equals(set2);
    }

    public boolean checkNoDuplicates(String[] array) {
        Set<String> set = new HashSet<>(Arrays.asList(array));
        return set.size() == array.length;
    }

    public boolean checkValidCharacters(String[] array) {
        for (String s : array) {
            if (!s.matches("[a-zA-Z0-9]+")) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        TreeNodeSolution solution = new TreeNodeSolution();
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入前序序列，元素之间用空格分隔：");
        String preOrderInput = scanner.nextLine();
        String[] preorder = preOrderInput.split(" ");

        System.out.println("请输入中序序列，元素之间用空格分隔：");
        String inOrderInput = scanner.nextLine();
        String[] inorder = inOrderInput.split(" ");

        if (preorder.length == 0 || inorder.length == 0) {
            System.out.println("错误：序列不能为空！");
            scanner.close();
            return;
        }

        if (preorder.length > MAX_LENGTH || inorder.length > MAX_LENGTH) {
            System.out.println("错误：序列长度不能超过" + MAX_LENGTH + "！");
            scanner.close();
            return;
        }

        if (preorder.length != inorder.length) {
            System.out.println("错误：前序序列和中序序列的元素个数必须一致！");
            scanner.close();
            return;
        }

        if (!solution.checkNoDuplicates(preorder) || !solution.checkNoDuplicates(inorder)) {
            System.out.println("错误：序列不能包含重复的元素！");
            scanner.close();
            return;
        }

        if (!solution.checkSameElements(preorder, inorder)) {
            System.out.println("错误：前序序列和中序序列的元素必须一致！");
            scanner.close();
            return;
        }

        if (!solution.checkValidCharacters(preorder) || !solution.checkValidCharacters(inorder)) {
            System.out.println("错误：序列只能包含数字和字母！");
            scanner.close();
            return;
        }

        if (!solution.checkSameType(preorder) || !solution.checkSameType(inorder)) {
            System.out.println("错误：序列中的元素类型必须一致！");
            scanner.close();
            return;
        }

        TreeNode tree = solution.buildTree(preorder, inorder);
        System.out.println("后序遍历的结果是：");
        solution.postorderTraversal(tree);
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.add(new TreePanel(tree));
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

        scanner.close();
    }
}