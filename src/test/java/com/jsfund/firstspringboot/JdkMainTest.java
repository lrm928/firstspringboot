package com.jsfund.firstspringboot;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * JDK 新特性测试
 * @author Administrator
 * @create 2023/4/30 19:35
 */
public class JdkMainTest {

    @Data
    @AllArgsConstructor
    private static class Person {
        private String id;
        private String name;
        private String sex;
        private int age;
    }

    @Data
    @AllArgsConstructor
    private static class PersonSub {
        private String name;
        private int age;
    }

    public static void main(String[] args) {
        //testMap();
        //testList();
        //testLocalDateTime();
        //testOptional();
        //testFunction();
        //testStream();
        testLambda();
    }

    /***
     * Lambda表达式的应用场景：任何有函数式接口的地方-只有一个抽象方式的接口就是函数式接口
     */
    private static void testLambda(){
        //匿名内部类线程实现试
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                System.out.println("匿名内部类线程实现方式");
//            }
//        }).start();

        //Lambda表达式线程实现方式
        new Thread(()->{System.out.println("Lambda表达式线程实现方式");}).start();

        List<String> list = Arrays.asList("java","python","scala","javascript");

        //普通匿名内部类方式
//        Collections.sort(list, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.length() - o2.length();
//            }
//        });

        //Lambda方式
        Collections.sort(list,(a,b) -> a.length() - b.length());
        list.forEach(System.out::println);

    }

    private static void testStream(){
        //Stream创建
        Stream<Integer> integerStream = Stream.of(1,2,3,4,5);
        integerStream.forEach(x -> System.out.println(x));

        //Collection集合创建-最常用的一种
        List<String> stringList = new ArrayList<>();
        stringList.add("str1");
        stringList.add("str2");
        stringList.add("str3");
        stringList.add("str4");
        Stream<String> stringStream = stringList.stream();
        stringStream.forEach(s -> {
            s = s+"-end";
            System.out.println(s);
        });

        //Array数组创建
        //通过Arrays.stream方法生成流，并且该方法生成的流是数值流【即IntStream】而不是 Stream对象流。数值流可以避免计算过程中拆箱装箱，提高性能
        //Stream API提供了mapToInt、mapToDouble、mapToLong三种方式将对象流【即Stream 】转换成对应的数值流，同时提供了boxed方法将数值流转换为对象流
        int[] intArr = {11,12,13,14,15};
        IntStream arrayIntStream = Arrays.stream(intArr);
        arrayIntStream.forEach(x -> System.out.println(x));

        //文件创建
        try {
            Stream<String> fileStream = Files.lines(Paths.get("src/data.txt"), Charset.defaultCharset());
            fileStream.forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //函数创建
        //iterate方法接受两个参数，第一个为初始化值，第二个为进行的函数操作，因为iterator生成的流为无限流，通过limit方法对流进行了截断，只生成5个偶数
        Stream<Integer> iterateStream = Stream.iterate(0,n->n+2).limit(5);
        iterateStream.forEach(x -> System.out.println(x));

        //generate方法接受一个参数，方法参数类型为Supplier ，由它为流提供值。generate生成的流也是无限流，因此通过limit对流进行了截断
        Stream<Double> generateStream = Stream.generate(Math::random).limit(5);
        generateStream.forEach(x -> System.out.println(x));

        //中间操作符
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        //1、filter用于通过设置的条件过滤出元素
        List filters = strings.stream().filter( x -> !x.isEmpty()).collect(Collectors.toList());
        filters.forEach(x -> System.out.println(x));
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("1", "jack", "boy",5));
        persons.add(new Person("2", "alice", "boy",5));
        persons.add(new Person("3", "tom", "boy",11));
        persons.add(new Person("4", "mary", "girl",12));
        //选择年龄大于6的人员
        List<Person> filetrUserList = persons.stream().filter(person -> person.getAge() > 6).collect(Collectors.toList());
        filetrUserList.forEach(System.out::println);


        //2、map 接受一个函数作为参数。这个函数会被应用到每个元素上，并将其映射成一个新的元素
        List maps = strings.stream().map(x -> x+"-IT").collect(Collectors.toList());
        maps.forEach(System.out::println);
        List<String> mapUserList = persons.stream().map(person -> person.getName() + "用户").collect(Collectors.toList());
        mapUserList.forEach(System.out::println);

        //3、distinct去重
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream().filter( i -> i%2 == 0).distinct().forEach(System.out::println);

        //4、sorted：排序，根据名字倒序
        persons.stream().sorted(Comparator.comparing(Person::getName).reversed()).collect(Collectors.toList()).forEach(System.out::println);

        //5、limit 返回不超过指定长度的流
        persons.stream().limit(3).collect(Collectors.toList()).forEach(System.out::println);

        //6、peek：对元素进行遍历处理，每个用户age加1输出
        persons.stream().peek(person -> person.setAge(person.getAge()+1)).forEach(System.out::println);

        //终端操作符 Stream流执行完终端操作之后，无法再执行其他动作，否则会报状态异常
        //一个流有且只能有一个终端操作，当这个操作执行后，流就被关闭了，无法再被操作，因此一个流只能被遍历一次，若想在遍历需要通过源数据在生成流
        //1.collect 收集器，将流转换为其他形式
        List<String> stringList1 = Arrays.asList("cv", "abd", "aba", "efg", "abcd","jkl", "jkl");

        //无序、不允许重复
        Set set = stringList1.stream().collect(Collectors.toSet());
        set.forEach(x -> System.out.println("set "+x));

        List list = stringList1.stream().collect(Collectors.toList());
        list.forEach(x -> System.out.println("list "+x));

        Map<String, String> map = stringList1.stream().collect(Collectors.toMap(v ->v.concat("_name"), v1 -> v1, (v1, v2) -> v1));
        map.forEach((K,V)-> System.out.println(K+"="+V));
    }

    private static void testFunction(){
        Function<Integer,String> convert = x -> String.valueOf(x);
        //apply()：接受一个参数并返回一个结果
        System.out.println("Integer->String: "+convert.apply(3)); //输出字符串"3"

        //compose():接受一个Function对象作为参数，并返回一个新的Function对象
        Function<Integer,Integer> add = x -> x+1;
        Function<Integer,Integer> multiply = x -> x*2;
        Function<Integer,Integer> addThenMultiply = multiply.compose(add);
        System.out.println("multiply.compose(add):"+addThenMultiply.apply(3));

        //andThen()方法与compose()方法类似，但是它先执行当前Function对象，然后将结果作为参数传递给传入的Function
        Function<Integer,Integer> andThenM = add.andThen(multiply);
        System.out.println("add.andThen(multiply):"+andThenM.apply(3));
    }

    private static void testLocalDateTime(){
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDate:"+localDate+"; year:"+localDate.getYear()+"; month:"+localDate.getMonth()+"; dayOfYear:"+localDate.getDayOfYear()+"; dayOfMonth:"+localDate.getDayOfMonth()+"; MonthValue:"+localDate.getMonthValue());
        System.out.println("localDateTime:"+localDateTime+"; year:"+localDateTime.getYear()+"; month:"+localDateTime.getMonth()+"; dayOfYear:"+localDateTime.getDayOfYear()+"; dayOfMonth:"+localDateTime.getDayOfMonth()+"; hour:"+localDateTime.getHour());

        ZoneId shangHaiZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime shanghaiDateTime = ZonedDateTime.now(shangHaiZoneId);
        System.out.println("亚洲上海此时的时间 : " + shanghaiDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        System.out.println("亚洲上海此时的时间 和时区: " + shanghaiDateTime);

        ZoneId americaZoneId= ZoneId.of("America/New_York");
        ZonedDateTime americaDateTime=ZonedDateTime.now(americaZoneId);
        System.out.println("美国纽约此时的时间 : " + americaDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        System.out.println("美国纽约此时的时间 和时区: " + americaDateTime);

        //时间格式化
        String dateformat = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("dateformat:"+dateformat);

        //时间计算
        LocalDate plus5Day = localDate.plusDays(5);
        System.out.println("plus5Day:"+plus5Day);
        LocalDateTime plus5DayTime = localDateTime.plusDays(5);
        System.out.println("plus5DayTime:"+plus5DayTime);
        LocalDate minus5Day = localDate.minusDays(5);

        //指定时间
        System.out.println("指定2035年的当前日期:"+localDate.withYear(2035));
        System.out.println("指定2035年的当前时间:"+localDateTime.withYear(2035));
        LocalDate theDate = LocalDate.of(2013, Month.NOVEMBER,21);
        LocalDateTime bithday = LocalDateTime.of(2013,11,21,20,48);
        System.out.println("theDate:"+theDate);
        System.out.println("bithday:"+bithday);

        //两个时间相差的年月日
        LocalDate ld1=LocalDate.parse("2022-11-17");
        LocalDate ld2=LocalDate.parse("2023-01-18");
        Period p= Period.between(ld1, ld2);
        System.out.println("相差年: "+p.getYears()+" 相差月 :"+p.getMonths() +" 相差天:"+p.getDays());
        //相差总数的时间 ChronoUnit也可以计算相差时分秒
        System.out.println("相差月份:"+ ChronoUnit.MONTHS.between(ld1, ld2));
        System.out.println("相差天数 : " + ChronoUnit.DAYS.between(ld1, ld2));

        //精度时间相差 Duration 这个类以秒和纳秒为单位建模时间的数量或数量
        Instant inst1 = Instant.now();
        System.out.println("当前时间戳 : " + inst1);
        Instant inst2 = inst1.plus(Duration.ofSeconds(10));
        System.out.println("增加之后的时间 : " + inst2);

        System.out.println("相差毫秒 : " + Duration.between(inst1, inst2).toMillis());
        System.out.println("相秒 : " + Duration.between(inst1, inst2).getSeconds());

    }

    private static void testList(){
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("a2");
        list.add("a3");
        list.add("a2");
        System.out.println("defalut: " + list);
        List unique = list.stream().distinct().collect(Collectors.toList());
        System.out.println("unique: " + unique);

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("1", "jack", "boy",5));
        persons.add(new Person("2", "jack", "boy",5));
        persons.add(new Person("3", "tom", "boy",11));
        persons.add(new Person("4", "mary", "girl",12));

        System.out.println("persons: "+persons);
        //根据name去重
        List<Person> uniquePerson = persons.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getName))),
                ArrayList::new
        ));
        System.out.println("uniquePerson: "+uniquePerson);

        //根据name和sex去重
        List<Person> nameSex = persons.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName() + ";" + o.getSex()))),
                ArrayList::new
        ));
        System.out.println("nameSex: " + nameSex);

        //filter()过滤列表
        List<Person> filterList = persons.stream().filter(p -> p.getSex().equals("boy")).collect(Collectors.toList());
        System.out.println("filterList: " + filterList);

        //list转map
        Map<String,Object> toMap = persons.stream().collect(Collectors.toMap(p->p.getId(),p->p.getName()));
        System.out.println("toMap: " + toMap);

        //获取指定对象某一属性，如获取人员改名list
        List<String> nameList = persons.stream().map(Person::getName).distinct().collect(Collectors.toList());
        System.out.println("nameList: " + nameList);

        // stream流实现数字计算
        List<Integer> ages = persons.stream().map(p-> p.getAge()+1).distinct().collect(Collectors.toList());
        System.out.println("ages: " + ages);

        //list->new list
        List<PersonSub> personSubList = persons.stream().map(p->{
            return new PersonSub(p.getName(),p.getAge());
        }).collect(Collectors.toList());
        System.out.println("personSubList: " + personSubList.toString());

    }

    /***
     * Optional 类主要解决的问题是臭名昭著的空指针异常
     */
    private static void testOptional(){
        Optional<String> empty = Optional.empty();
        System.out.println("empty:"+empty); // 输出：Optional.empty

        String name = null;
        System.out.println("name:"+name);
        //明确对象不为 null  的时候使用 of()。
        //如果对象即可能是 null 也可能是非 null，你就应该使用 ofNullable() 方法
        System.out.println("Optional.ofNullable(name):"+Optional.ofNullable(name).orElse("默认值"));
        //isPresent() 判断一个 Optional 对象是否存在，如果存在，该方法返回 true，否则返回 false——取代了 obj != null 的判断
        System.out.println("Optional.ofNullable(name).isPresent():"+Optional.ofNullable(name).isPresent());

        //ifPresent 不为空时执行
        Optional.of("沉默王二").ifPresent(str -> System.out.println(str.length()));

        //过滤值filter
        Predicate<String> len6 = pwd -> pwd.length() >= 6; //密码长度大于等于6
        Predicate<String> len10 = pwd -> pwd.length() <= 10; //密码长度小于等于10
        //要求密码在6-10位之间
        String password = "PassWord12";
        Optional<String> passwordOpt = Optional.ofNullable(password);
        boolean validPwd = passwordOpt.filter(len6.and(len10)).isPresent();
        System.out.println("密码长度是否合法："+validPwd);

        //转换值map
        Predicate<String> neqPassword = pwd -> !pwd.equals("password"); //密码不等于password
        boolean result = passwordOpt.map(String::toLowerCase).filter(len6.and(len10 ).and(neqPassword)).isPresent();
        System.out.println("密码是否合法："+result);

    }

    private static void testMap(){
        Map<String,Object> map= new HashMap<>();
        map.put("k1",1);
        map.put("k2",null);
        map.put("k3","3");
        map.put("k4","4");
        map.forEach((k,v)->{
            System.out.println("键："+k+",值："+v);
        });
        System.out.println("1**************************************");
        map.put("k1","v1");
        //原有的Put方法，是当Key存在时则替换；而putIfAbsent方法，则是当存在Key忽略Put操作不替换
        map.putIfAbsent("k2","v2");//k存在、值不存在 设置
        map.putIfAbsent("k3","v3");//K值存在忽略
        map.put("k4","v4"); //K值存在替换
        map.putIfAbsent("k5","5");//K值不存在设置
        map.put("k6","6");//K值不存在设置
        map.forEach((k,v)->{
            System.out.println("键："+k+",值："+v);
        });
        System.out.println("2**************************************");
        // 只有当Map中键值对同时等于参数Key和Value时才执行删除
        map.remove("k1", "1"); //kv不同等 删除不了
        map.remove("k2", "v2");//kv等同，删除
        // 如果Key存在，则将函数的运算结果作为这个Key对应的Value的新值Put进去
        map.computeIfPresent("k3", (k, v) -> k + v);//K存在计算设置
        map.computeIfPresent("k7",(k, v) -> k + v);//K不存在，忽略
        map.compute("k8",(k, v) -> k + "8");//设置

        // 如果Map中不存这个Key对应个的键值对，则Put这个Key和将Key带入函数运算后的结果为Value的键值对；如果Key存在，则忽略Put操作
        map.computeIfAbsent("k4", k -> k+"4");//k值存在，忽略
        map.computeIfAbsent("k9", k -> k +"9");//不存在计算

        // 将Map中指定Key的Value设置为将存在值与传入值通过函数运算后的结果的新值
        map.merge("k5", "pv5", (ov, pv) -> ov.toString()+pv.toString());//kv存在，计算更新
        map.merge("k10", "pv10", (ov, pv) -> ov.toString()+pv.toString());//kv不存在，设备

        map.forEach((k,v)->{
            System.out.println("键："+k+",值："+v);
        });

        // 从Map中获取指定Key的Value的值，如果不存在则返回指定的默认值
        Object vle1 = map.getOrDefault("k1", "def1");
        Object vle2 = map.getOrDefault("k2", "def2");
        System.out.println(vle1+"  "+vle2);

    }
}
