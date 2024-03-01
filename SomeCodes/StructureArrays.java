import java.util.*;

public class StructureArrays {
    public enum Gender {
        BOY, GIRL
    }
    public int id;
    public String name;
    public Gender gender;
    public int age;
    public StructureArrays(int id,String name,Gender gender,int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
    public static void delete(ArrayList<StructureArrays> list1,ArrayList<StructureArrays> list2) {
        for (int i=0;i<list1.size();i++) {
            for (int j=0;j<list2.size();j++) {
                if (list1.get(i).id == list2.get(j).id) {
                    list1.remove(i);
                    i--; // 重新检查当前位置
                    break; // 跳出内层循环
                }
            }
        }
        sort(list1); // 删除结束后重新排序
    }

    public static void sort(ArrayList<StructureArrays> list) {
        Collections.sort(list, new Comparator<StructureArrays>() {
            @Override
            public int compare(StructureArrays o1, StructureArrays o2) {
                // 先按性别排序，男性在前，女性在后
                int genderCompare = o1.gender.compareTo(o2.gender);
                if (genderCompare != 0) {
                    return genderCompare;
                }

                // 如果性别相同，再按id升序排序
                return Integer.compare(o1.id, o2.id);
            }
        });
    }
    public static void merge(ArrayList<StructureArrays> list1,ArrayList<StructureArrays> list2) {
        for (int i=0;i<list2.size();i++) {
            list1.add(list2.get(i));
        }
        sort(list1);
    }
    @Override
    public String toString() {
        return "StructureArrays{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
    public static void display(ArrayList<StructureArrays> list) {
        for (int i=0;i<list.size();i++) {
            System.out.println(list.get(i));
        }
    }
    public static ArrayList<StructureArrays> readLines(Scanner sc) {
        var list = new ArrayList<StructureArrays>();
        System.out.print("请输入总人数：");
        int n = sc.nextInt(); // 总人数
        System.out.println("请输入每个人的信息（格式：id name gender age）：");
        sc.nextLine(); // 清除输入缓冲区
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            Gender gender = Gender.valueOf(parts[2].toUpperCase());
            int age = Integer.parseInt(parts[3]);
            list.add(new StructureArrays(id, name, gender, age));
        }
        return list;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        var list1 = readLines(sc);
        var list2 = readLines(sc);
        System.out.println("list1:");
        display(list1);
        System.out.println("list2:");
        display(list2);
        System.out.println("删除list1中与list2中相同id的人：");
        delete(list1,list2);
        display(list1);
        System.out.println("合并list1和list2：");
        merge(list1,list2);
        display(list1);
    }

}
