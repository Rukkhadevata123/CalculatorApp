import java.util.*;

public class CommandTest {
    public static void main(String[] args) {
        // 创建一个Scanner对象来接收用户输入
        Scanner sc = new Scanner(System.in);

        // 获取用户输入的规则
        String rule = sc.nextLine();

        // 获取用户输入的命令
        String command = sc.nextLine();

        // 将命令按空格分割成字符串数组
        String[] commandArray = command.split(" ");

        // 打印出数组的第一个元素
        System.out.println(commandArray[0]);

        // 将数组转换为ArrayList
        List<String> list = new ArrayList<>(Arrays.asList(commandArray));

        // 如果数组的第二个元素不以'-'开头，则移除该元素
        if (commandArray[1].charAt(0) != '-') {
            list.remove(1);
        }

        // 创建一个LinkedHashMap用于存储规则
        // 键为字符，值为布尔类型，表示该选项是否需要参数
        Map<Character, Boolean> map = getCharacterBooleanMap(rule);

        // 遍历命令数组中除第一个元素外的所有元素
        for (int i = 1; i < list.size(); i++) {
            // 如果map中不包含当前元素的第二个字符作为键，则打印出选项不存在的错误信息并跳出循环
            if (!map.containsKey(list.get(i).charAt(1))) {
                System.out.println(commandArray[0] + ": invalid option -- '" + list.get(i).charAt(1) + "'");
                break;
            }

            // 如果map中包含当前元素的第二个字符作为键，并且对应值为true，但是当前元素后面没有下一个元素，则打印出缺少参数的错误信息并跳出循环
            if (map.containsKey(list.get(i).charAt(1)) && map.get(list.get(i).charAt(1)) && i + 1 >= list.size()) {
                System.out.println(commandArray[0] + ": option requires an argument -- '" + list.get(i).charAt(1) + "'");
                break;
            }

            // 如果map中包含当前元素的第二个字符作为键，并且对应值为false，则只打印出该选项的字符
            if (map.containsKey(list.get(i).charAt(1)) && !map.get(list.get(i).charAt(1))) {
                System.out.println(list.get(i).charAt(1));

                // 跳过当前选项的参数，继续下一次循环
                while (i + 1 < list.size() && list.get(i + 1).charAt(0) != '-') {
                    i++;
                }
            }

            // 如果map中包含当前元素的第二个字符作为键，并且对应值为true，则打印出该选项的字符和参数
            else if (map.containsKey(list.get(i).charAt(1)) && map.get(list.get(i).charAt(1))) {
                System.out.println(list.get(i).charAt(1) + "=" + list.get(i + 1));
                i++;
            }
        }
        sc.close();
        
        /*
        for(Map.Entry<Character, Boolean> entry : map.entrySet()){
            Character key = entry.getKey();
            Boolean value = entry.getValue();
            System.out.println(key + ":" + value);
        }
        */
    }

    private static Map<Character, Boolean> getCharacterBooleanMap(String rule) {
        Map<Character, Boolean> map = new LinkedHashMap<>();

        // 遍历规则字符串
        for (int i = 0; i < rule.length(); i++) {
            // 判断规则中的字符是否为':'的前一个字符
            if (i < rule.length() - 1 && rule.charAt(i + 1) == ':') {
                // 如果是，将该字符作为键，值为true存入map中
                map.put(rule.charAt(i), true);
                i++;
            } else {
                // 否则，将该字符作为键，值为false存入map中
                map.put(rule.charAt(i), false);
            }
        }
        return map;
    }
}
