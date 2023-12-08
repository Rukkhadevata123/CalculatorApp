public class Broken {
    public static void main(String[] args) {
        String[] people = {"A", "B", "C", "D"};

        // 遍历所有可能的情况
        for (int i = 0; i < people.length; i++) {
            // 假设第i个人打烂了玻璃
            String guiltyPerson = people[i];

            // 记录说真话的人数
            int trueCount = 0;

            // 遍历每个人的陈述
            for (int j = 0; j < people.length; j++) {
                if (j == i) {
                    // 第i个人说谎了
                    continue;
                }

                // 判断每个人的陈述是否与已知条件相符
                if ((people[j].equals("A") && !guiltyPerson.equals("A")) ||
                        (people[j].equals("B") && guiltyPerson.equals("C")) ||
                        (people[j].equals("C") && guiltyPerson.equals("D")) ||
                        (people[j].equals("D") && !guiltyPerson.equals("D"))) {
                    trueCount++;
                }
            }

            // 如果有三个人说了真话，且只有一个人说了假话，那么找到了答案
            if (trueCount == 3) {
                System.out.println("打烂玻璃的人是：" + guiltyPerson);
                break;
            }
        }
    }
}
