/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testlambda;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Jaime Zapata - nebulae.com.co
 */
public class TestLambda {

    private BiConsumer<Integer, JSONObject> consumer = null;

    private Period selectedPeriod = Period.HOUR;

    private Date selectedDate1Filter;

    private Date selectedDate2Filter;

    public enum Period {
        HOUR, DAY, MONTH
    };

    public BiConsumer<Integer, JSONObject> getBiConsumer() {
        if (consumer == null) {
            consumer = (value, jsonAcumulator) -> {
                System.out.println("CONSUMER -> value: " + value);
                jsonAcumulator.put("count", jsonAcumulator.getInt("count") + value);

                if (jsonAcumulator.getInt("count") > 100) {
                    try {
                        //throw new RuntimeException();
                        throw new Exception();
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }

                    //throw new Exception("");
                }
            };
        }
        return consumer;
    }

    public void sumValues() {
        Consumer<Integer> consumer1 = (value) -> {
            System.out.println("CONSUMER -> value: " + value);

        };

        List<Integer> values = Arrays.asList(0, 10, 20, 40, 30, 54, 87, 54, 534, 2, 645, 423653, 3356);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("count", 0);
        
        values.parallelStream().forEach(consumer1);
        
//        for (int i = 0; i < values.size(); i++) {
//            System.out.println("i -> " + i);
//            consumer1.accept(values.get(i));
//        }
        System.out.println("TERMINO");
    }

    public void test() {
        
        
        
        System.out.println(Pattern.compile("hola").pattern());
        
        
        
        
        
        
        ZoneId zoneId = ZoneId.of("GMT+5");
        
        LocalDateTime lastLocalDateHour = LocalDateTime.now(zoneId).withHour(0).withMinute(0).withSecond(0).withNano(0);
        
        System.out.println("text: "+lastLocalDateHour);
        
        System.out.println("text: "+lastLocalDateHour.atZone(zoneId).toInstant().toEpochMilli());
    }

    public static class Test {

        /**
         * @return the value
         */
        public Integer getValue() {
            return value;
        }

        public Double getQualifier() {
            return ((double) value / (double) value2);
        }

        /**
         * @param value the value to set
         */
        public void setValue(Integer value) {
            this.value = value;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        private Integer value;

        private Integer value2;

        private String name;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + Objects.hashCode(this.value);
            hash = 19 * hash + Objects.hashCode(this.name);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Test other = (Test) obj;
            if (!Objects.equals(this.name, other.name)) {
                return false;
            }
            if (!Objects.equals(this.value, other.value)) {
                return false;
            }
            return true;
        }

        /**
         * @return the value2
         */
        public Integer getValue2() {
            return value2;
        }

        /**
         * @param value2 the value2 to set
         */
        public void setValue2(Integer value2) {
            this.value2 = value2;
        }

    }

    /**
     * Generates the ranges of reports to find reports
     *
     * @param calendar
     */
    private void generateReportDates(Calendar calendar, List<Long> tripReportTimeStampList) {
        switch (selectedPeriod) {
            case HOUR:
                calendar.add(Calendar.HOUR, 1);
                break;
            case DAY:
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                break;
            case MONTH:
                calendar.add(Calendar.MONTH, 1);
                break;
        }

        System.out.println("Compare: selectedDate2Filter -> " + new Date(selectedDate2Filter.getTime()) + "  ---  " + calendar.getTime());

        if (selectedDate2Filter.getTime() > calendar.getTimeInMillis()) {
            tripReportTimeStampList.add(selectedDate1Filter.getTime());
            generateReportDates(calendar, tripReportTimeStampList);
        }
    }

        public static String generateCheckDigit(String card) {
        if (card == null) {
            return null;
        }
        // convert to array of int for simplicity 
        int[] digits = new int[card.length()];
        for (int i = 0; i < card.length(); i++) {
            digits[i] = Character.getNumericValue(card.charAt(i));
        }

        // double every other starting from right - jumping from 2 in 2
        for (int i = digits.length - 1; i >= 0; i -= 2) {
            digits[i] += digits[i];

            // taking the sum of digits grater than 10 - simple trick by substract 9
            if (digits[i] >= 10) {
                digits[i] = digits[i] - 9;
            }
        }
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            sum += digits[i];
        }
        // multiply by 9 step
        sum = sum * 9;

        // convert to string to be easier to take the last digit
        String digit = sum + "";
        return digit.substring(digit.length() - 1);
    }

    /**
     * Verify if account number is valid
     * @param ccNumber
     * @return 
     */
    public static boolean validateAccountNumber(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
    
    public static void main(String[] args) {
        TestLambda lambda = new TestLambda();
        lambda.test();
//        Long lastTripReportMillisHour = 0l;
//
//
//
//        
//        LocalDateTime lastLocalDateHour = lastTripReportMillisHour == 0 ? LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0)
//                : LocalDateTime.ofInstant(Instant.ofEpochMilli((lastTripReportMillisHour)), ZoneId.systemDefault()).withMinute(0).withSecond(0).withNano(0);
//
//        
//        System.out.println(lastLocalDateHour.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//        List<Long> tripReportTimeStampList = new ArrayList<>();
//        
//        TestLambda testLambda = new TestLambda();
//        testLambda.selectedPeriod = Period.HOUR;
//        testLambda.selectedDate1Filter = new Date(1492722000000l);
//        testLambda.selectedDate2Filter = new Date(1492743600000l);
//        
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(testLambda.selectedDate1Filter);
//        
//        testLambda.generateReportDates(calendar, tripReportTimeStampList);
//        
//        for (Long long1 : tripReportTimeStampList) {
//            System.out.println("Date -> "+ new Date(long1));
//        }
//        String jsonTest = "{\"count\":9665,\"zones\":{\"SANTANDER\":{\"CANCELLED_DRIVER\":4,\"DONE\":11,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":2,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":1},\"count\":17},\"CRISTOBAL COLON\":{\"CANCELLED_DRIVER\":45,\"DONE\":105,\"CANCELLED_CLIENT\":8,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":3,\"VEHICLE_FROM_OTHER_COMPANY\":6,\"IVR\":6,\"INVALID_ADDRESS\":1,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":10,\"ASSIGNED_TIMEOUT\":2,\"CONGESTION_ON_THE_ROAD\":13,\"USER_DOESNT_ANSWER\":9},\"count\":158},\"CALDAS\":{\"CANCELLED_DRIVER\":5,\"DONE\":16,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"IVR\":3,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":2},\"count\":25},\"EL LIDO\":{\"CANCELLED_DRIVER\":35,\"DONE\":64,\"CANCELLED_CLIENT\":22,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"MECHANICAL_FAILURE\":1,\"IVR\":17,\"DIFFERENT_TRIP_SERVICE\":1,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":7,\"ASSIGNED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":4,\"CONGESTION_ON_THE_ROAD\":15,\"USER_DOESNT_ANSWER\":5,\"USER_CANCELLED_TRIP\":1},\"count\":121},\"SAN JUAN BOSCO\":{\"CANCELLED_DRIVER\":7,\"DONE\":20,\"CANCELLED_CLIENT\":10,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":9,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":1},\"count\":37},\"PRIMERO DE MAYO\":{\"CANCELLED_DRIVER\":31,\"DONE\":53,\"CANCELLED_CLIENT\":9,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"IVR\":4,\"INVALID_ADDRESS\":6,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":4,\"TRIP_NOTIFICATION_TTL\":4,\"USER_DID_NOT_WAIT\":1,\"CONGESTION_ON_THE_ROAD\":10,\"USER_DOESNT_ANSWER\":6},\"count\":93},\"LAS ACACIAS\":{\"CANCELLED_DRIVER\":17,\"DONE\":42,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"MECHANICAL_FAILURE\":1,\"IVR\":2,\"INVALID_ADDRESS\":2,\"ASSIGNED_TIMEOUT\":3,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":3},\"count\":62},\"CIUDAD JARDIN\":{\"CANCELLED_DRIVER\":56,\"DONE\":84,\"CANCELLED_CLIENT\":48,\"cancellationCauses\":{\"IVR\":33,\"INVALID_ADDRESS\":3,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":11,\"TRIP_NOTIFICATION_TTL\":14,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":23,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":7},\"count\":188},\"SANTA MONICA POPULAR\":{\"CANCELLED_DRIVER\":17,\"DONE\":25,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"MECHANICAL_FAILURE\":1,\"IVR\":3,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":8,\"IT_TAKES_TOO_MUCH_TIME\":1,\"USER_DOESNT_ANSWER\":4},\"count\":46},\"none\":{\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"TRIP_NOTIFICATION_TTL\":1},\"count\":1},\"CALIPSO\":{\"CANCELLED_DRIVER\":2,\"DONE\":5,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"IVR\":4,\"USER_IS_NOT_HERE\":2},\"count\":11},\"INDUSTRIAL\":{\"CANCELLED_DRIVER\":13,\"DONE\":20,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":1,\"TRIP_NOTIFICATION_TTL\":1,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":5,\"USER_DOESNT_ANSWER\":4},\"count\":35},\"GUABAL\":{\"CANCELLED_DRIVER\":25,\"CANCELLED_OPERATOR\":1,\"DONE\":58,\"CANCELLED_CLIENT\":21,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"DIFFERENT_TRIP_SERVICE\":1,\"IVR\":17,\"PRE_ASSIGNED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":3,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DOESNT_ANSWER\":4,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"DRUNK_USER\":1,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":3,\"USER_IS_NOT_HERE\":3,\"USER_DID_NOT_WAIT\":1},\"count\":105},\"BRETAÑA\":{\"CANCELLED_DRIVER\":20,\"DONE\":46,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"IVR\":2,\"ASSIGNED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":3,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":6},\"count\":69},\"SAN NICOLAS\":{\"CANCELLED_DRIVER\":25,\"DONE\":52,\"CANCELLED_CLIENT\":9,\"cancellationCauses\":{\"IVR\":9,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":4,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":12,\"USER_DOESNT_ANSWER\":3},\"count\":86},\"MANUELA BELTRAN 1\":{\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1},\"count\":1},\"CIUDAD MODELO\":{\"CANCELLED_DRIVER\":21,\"DONE\":31,\"CANCELLED_CLIENT\":12,\"cancellationCauses\":{\"IVR\":8,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":2,\"ASSIGNED_TIMEOUT\":3,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":10,\"USER_DID_NOT_WAIT\":2,\"USER_DOESNT_ANSWER\":3},\"count\":64},\"EL DIAMANTE\":{\"CANCELLED_DRIVER\":6,\"DONE\":30,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"IVR\":2,\"USER_IS_NOT_HERE\":2,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":1},\"count\":40},\"MELENDEZ\":{\"CANCELLED_DRIVER\":20,\"DONE\":34,\"CANCELLED_CLIENT\":15,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":15,\"USER_IS_NOT_HERE\":4,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":8,\"USER_DOESNT_ANSWER\":4},\"count\":69},\"COMUNEROS\":{\"CANCELLED_DRIVER\":7,\"DONE\":10,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"IVR\":5,\"INVALID_ADDRESS\":3,\"ARRIVED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":1},\"count\":23},\"OESTE\":{\"CANCELLED_DRIVER\":19,\"DONE\":19,\"CANCELLED_CLIENT\":35,\"cancellationCauses\":{\"IVR\":23,\"TRIP_NOTIFICATION_TTL\":10,\"USER_IS_NOT_HERE\":3,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":6},\"count\":73},\"PANCE\":{\"CANCELLED_DRIVER\":52,\"DONE\":109,\"CANCELLED_CLIENT\":66,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":3,\"MECHANICAL_FAILURE\":2,\"IVR\":44,\"TRIP_NOTIFICATION_TTL\":14,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":16,\"USER_DOESNT_ANSWER\":2,\"USER_CANCELLED_TRIP\":1,\"VEHICLE_FROM_OTHER_COMPANY\":4,\"INVALID_ADDRESS\":3,\"ARRIVED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":12,\"ASSIGNED_TIMEOUT\":2,\"USER_DID_NOT_WAIT\":4},\"count\":227},\"BEJAMIN HERRERA\":{\"CANCELLED_DRIVER\":10,\"DONE\":19,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"DRUNK_USER\":1,\"IVR\":2,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DOESNT_ANSWER\":3},\"count\":31},\"ARANJUEZ\":{\"CANCELLED_DRIVER\":20,\"DONE\":73,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":4,\"MECHANICAL_FAILURE\":2,\"IVR\":4,\"DIFFERENT_TRIP_SERVICE\":1,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":4,\"CONGESTION_ON_THE_ROAD\":4,\"IT_TAKES_TOO_MUCH_TIME\":1,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":1},\"count\":99},\"PILOTO\":{\"CANCELLED_DRIVER\":4,\"DONE\":1,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":6},\"CALIMA\":{\"CANCELLED_DRIVER\":5,\"DONE\":5,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":1},\"count\":10},\"CRISTALES\":{\"CANCELLED_DRIVER\":3,\"DONE\":14,\"CANCELLED_CLIENT\":18,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"MECHANICAL_FAILURE\":1,\"IVR\":10,\"TRIP_NOTIFICATION_TTL\":5,\"USER_IS_NOT_HERE\":2,\"USER_DID_NOT_WAIT\":1,\"CONGESTION_ON_THE_ROAD\":1},\"count\":35},\"VALLE DE LILI\":{\"CANCELLED_DRIVER\":56,\"DONE\":112,\"CANCELLED_CLIENT\":40,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":3,\"USER_DOESNT_REQUIRE_VEHICLE\":2,\"IVR\":32,\"DIFFERENT_TRIP_SERVICE\":1,\"TRIP_NOTIFICATION_TTL\":4,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":9,\"USER_DOESNT_ANSWER\":8,\"VEHICLE_FROM_OTHER_COMPANY\":2,\"DRUNK_USER\":1,\"ARRIVED_TIMEOUT\":3,\"USER_IS_NOT_HERE\":12,\"ASSIGNED_TIMEOUT\":4,\"IT_TAKES_TOO_MUCH_TIME\":1},\"count\":208},\"SANTA ANITA\":{\"CANCELLED_DRIVER\":28,\"CANCELLED_OPERATOR\":1,\"DONE\":81,\"CANCELLED_CLIENT\":14,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"IVR\":11,\"PRE_ASSIGNED_TIMEOUT\":1,\"ASSIGNED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":3,\"TRIP_NOTIFICATION_TTL\":3,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":15,\"USER_DOESNT_ANSWER\":2},\"count\":124},\"LA FLORA\":{\"CANCELLED_DRIVER\":25,\"DONE\":43,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"MECHANICAL_FAILURE\":1,\"IVR\":5,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":7,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":9,\"USER_DOESNT_ANSWER\":5},\"count\":74},\"CIUDAD 2000\":{\"CANCELLED_DRIVER\":27,\"DONE\":57,\"CANCELLED_CLIENT\":34,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"VEHICLE_FROM_OTHER_COMPANY\":3,\"DRUNK_USER\":1,\"IVR\":32,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":2,\"ASSIGNED_TIMEOUT\":1,\"USER_DID_NOT_WAIT\":1,\"CONGESTION_ON_THE_ROAD\":10,\"USER_DOESNT_ANSWER\":3},\"count\":118},\"ALFONSO LOPEZ 2\":{\"CANCELLED_DRIVER\":3,\"DONE\":4,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":7},\"LA CAMPIÑA\":{\"CANCELLED_DRIVER\":17,\"DONE\":22,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"INVALID_ADDRESS\":3,\"ASSIGNED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":4,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DOESNT_ANSWER\":1},\"count\":40},\"BOSQUES DEL LIMONAR\":{\"CANCELLED_DRIVER\":6,\"DONE\":21,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"TRIP_NOTIFICATION_TTL\":1,\"USER_IS_NOT_HERE\":2,\"USER_DOESNT_ANSWER\":1},\"count\":28},\"VILLA  DEL PRADO\":{\"DONE\":1,\"count\":1},\"PETECUY\":{\"CANCELLED_DRIVER\":3,\"DONE\":3,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"IVR\":3,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":9},\"LA CAROLINA\":{\"CANCELLED_DRIVER\":5,\"DONE\":12,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":2},\"count\":19},\"MUNICIPAL\":{\"CANCELLED_DRIVER\":6,\"DONE\":18,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"USER_IS_NOT_HERE\":2,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":1},\"count\":25},\"SAN ANTONIO\":{\"CANCELLED_DRIVER\":36,\"DONE\":96,\"CANCELLED_CLIENT\":26,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":22,\"INVALID_ADDRESS\":2,\"ARRIVED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":8,\"ASSIGNED_TIMEOUT\":5,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DID_NOT_WAIT\":2,\"USER_DOESNT_ANSWER\":6},\"count\":158},\"COLSEGUROS\":{\"CANCELLED_DRIVER\":74,\"DONE\":156,\"CANCELLED_CLIENT\":14,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"IVR\":11,\"DIFFERENT_TRIP_SERVICE\":1,\"BRING_PET\":3,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":20,\"USER_DOESNT_ANSWER\":6,\"VEHICLE_FROM_OTHER_COMPANY\":9,\"DRUNK_USER\":1,\"INVALID_ADDRESS\":7,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":10,\"ASSIGNED_TIMEOUT\":4,\"USER_DID_NOT_WAIT\":2},\"count\":244},\"PRADOS DEL NORTE\":{\"CANCELLED_DRIVER\":12,\"DONE\":25,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":2,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DOESNT_ANSWER\":3},\"count\":39},\"OMAR TORRIJOS\":{\"CANCELLED_DRIVER\":7,\"DONE\":10,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":3,\"INVALID_ADDRESS\":2,\"CONGESTION_ON_THE_ROAD\":2},\"count\":20},\"EL JARDIN\":{\"CANCELLED_DRIVER\":22,\"DONE\":48,\"CANCELLED_CLIENT\":9,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"IVR\":9,\"INVALID_ADDRESS\":3,\"ARRIVED_TIMEOUT\":1,\"ASSIGNED_TIMEOUT\":3,\"USER_IS_NOT_HERE\":4,\"CONGESTION_ON_THE_ROAD\":6,\"USER_DOESNT_ANSWER\":2},\"count\":79},\"ALFONSO BARBERENA\":{\"CANCELLED_DRIVER\":7,\"DONE\":7,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"USER_IS_NOT_HERE\":2,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":2},\"count\":15},\"CAÑAVERALEJO\":{\"CANCELLED_DRIVER\":3,\"DONE\":5,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":9},\"BELALCAZAR\":{\"CANCELLED_DRIVER\":16,\"DONE\":30,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"ARRIVED_TIMEOUT\":1,\"ASSIGNED_TIMEOUT\":3,\"USER_IS_NOT_HERE\":2,\"USER_DID_NOT_WAIT\":1,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DOESNT_ANSWER\":3},\"count\":48},\"BELLA SUIZA\":{\"CANCELLED_DRIVER\":17,\"DONE\":37,\"CANCELLED_CLIENT\":25,\"cancellationCauses\":{\"DRUNK_USER\":1,\"MECHANICAL_FAILURE\":1,\"IVR\":17,\"INVALID_ADDRESS\":2,\"TRIP_NOTIFICATION_TTL\":7,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":6,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":2},\"count\":79},\"20 DE JULIO\":{\"CANCELLED_DRIVER\":7,\"DONE\":7,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":1,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":3},\"count\":15},\"VERSALLES - CLINICA VERSALLES\":{\"CANCELLED_DRIVER\":7,\"DONE\":31,\"cancellationCauses\":{\"USER_IS_NOT_HERE\":3,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":1},\"count\":38},\"CHAMPAGNAT\":{\"CANCELLED_DRIVER\":14,\"DONE\":59,\"CANCELLED_CLIENT\":11,\"cancellationCauses\":{\"IVR\":11,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":3,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":5,\"USER_DOESNT_ANSWER\":2},\"count\":84},\"ALTO JORDAN\":{\"CANCELLED_DRIVER\":2,\"DONE\":2,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1},\"count\":5},\"MARROQUIN 1\":{\"DONE\":2,\"count\":2},\"MARROQUIN 2\":{\"CANCELLED_DRIVER\":6,\"DONE\":4,\"CANCELLED_CLIENT\":9,\"cancellationCauses\":{\"IVR\":7,\"INVALID_ADDRESS\":1,\"TRIP_NOTIFICATION_TTL\":2,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":1},\"count\":19},\"SAN FERNANDO VIEJO\":{\"CANCELLED_DRIVER\":104,\"DONE\":155,\"CANCELLED_CLIENT\":81,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":3,\"MECHANICAL_FAILURE\":8,\"IVR\":62,\"SERVICE_CODE\":1,\"BRING_PET\":1,\"TRIP_NOTIFICATION_TTL\":9,\"CONGESTION_ON_THE_ROAD\":43,\"USER_DOESNT_ANSWER\":7,\"USER_CANCELLED_TRIP\":1,\"VEHICLE_FROM_OTHER_COMPANY\":7,\"INVALID_ADDRESS\":5,\"ARRIVED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":14,\"ASSIGNED_TIMEOUT\":4,\"USER_REQUIRES_HIGHER_CAPACITY_VEHICLE\":1,\"USER_DID_NOT_WAIT\":4},\"count\":340},\"TORRES DE CONFANDY\":{\"CANCELLED_DRIVER\":2,\"DONE\":3,\"cancellationCauses\":{\"USER_IS_NOT_HERE\":1,\"ASSIGNED_TIMEOUT\":1},\"count\":5},\"TRONCAL\":{\"CANCELLED_DRIVER\":22,\"DONE\":49,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":2,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":4,\"TRIP_NOTIFICATION_TTL\":1,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":6},\"count\":74},\"ANTONIO NARIÑO\":{\"CANCELLED_DRIVER\":14,\"DONE\":37,\"CANCELLED_CLIENT\":12,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":8,\"DIFFERENT_TRIP_SERVICE\":1,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":3,\"TRIP_NOTIFICATION_TTL\":2,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":1},\"count\":63},\"AGUA BLANCA\":{\"CANCELLED_DRIVER\":7,\"DONE\":9,\"CANCELLED_CLIENT\":5,\"cancellationCauses\":{\"IVR\":4,\"DIFFERENT_TRIP_SERVICE\":1,\"USER_IS_NOT_HERE\":1,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":1},\"count\":21},\"EL RETIRO\":{\"CANCELLED_DRIVER\":10,\"DONE\":20,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":6,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":3,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":2},\"count\":36},\"LA INDEPENDENCIA\":{\"CANCELLED_DRIVER\":7,\"DONE\":40,\"CANCELLED_CLIENT\":5,\"cancellationCauses\":{\"IVR\":5,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":3,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":1},\"count\":52},\"EL CEDRO\":{\"CANCELLED_DRIVER\":23,\"DONE\":30,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":4,\"USER_IS_NOT_HERE\":4,\"ASSIGNED_TIMEOUT\":3,\"TRIP_NOTIFICATION_TTL\":1,\"BRING_PET\":2,\"CONGESTION_ON_THE_ROAD\":10,\"IT_TAKES_TOO_MUCH_TIME\":1,\"USER_DOESNT_ANSWER\":1},\"count\":59},\"VILLA COLOMBIA\":{\"CANCELLED_DRIVER\":13,\"DONE\":26,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":1,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":2},\"count\":40},\"REFUGIO\":{\"CANCELLED_DRIVER\":47,\"DONE\":124,\"CANCELLED_CLIENT\":38,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":31,\"INVALID_ADDRESS\":2,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":8,\"ASSIGNED_TIMEOUT\":2,\"TRIP_NOTIFICATION_TTL\":5,\"CONGESTION_ON_THE_ROAD\":18,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":9},\"count\":209},\"BUENO MADRID\":{\"CANCELLED_DRIVER\":1,\"DONE\":3,\"cancellationCauses\":{\"USER_IS_NOT_HERE\":1},\"count\":4},\"NAPOLES\":{\"CANCELLED_DRIVER\":17,\"DONE\":69,\"CANCELLED_CLIENT\":27,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":26,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":2,\"TRIP_NOTIFICATION_TTL\":1,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":6,\"USER_DOESNT_ANSWER\":1},\"count\":113},\"ALAMEDA\":{\"CANCELLED_DRIVER\":29,\"DONE\":38,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"DRUNK_USER\":1,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":5,\"INVALID_ADDRESS\":3,\"ARRIVED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":1,\"ASSIGNED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":1,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":15},\"count\":73},\"EL TREBOL\":{\"CANCELLED_DRIVER\":5,\"DONE\":13,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"IVR\":3,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":2},\"count\":21},\"PAMPALINDA\":{\"CANCELLED_DRIVER\":42,\"DONE\":141,\"CANCELLED_CLIENT\":19,\"cancellationCauses\":{\"DRUNK_USER\":1,\"IVR\":16,\"INVALID_ADDRESS\":3,\"ARRIVED_TIMEOUT\":2,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":5,\"BRING_PET\":2,\"TRIP_NOTIFICATION_TTL\":2,\"CONGESTION_ON_THE_ROAD\":18,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":3},\"count\":202},\"JORGE ISAAC\":{\"CANCELLED_DRIVER\":4,\"DONE\":2,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":7},\"VALLE GRANDE\":{\"DONE\":7,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"TRIP_NOTIFICATION_TTL\":1},\"count\":8},\"LAS GRANJAS\":{\"CANCELLED_DRIVER\":23,\"DONE\":53,\"CANCELLED_CLIENT\":7,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"MECHANICAL_FAILURE\":4,\"IVR\":4,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":4,\"CONGESTION_ON_THE_ROAD\":9,\"USER_DOESNT_ANSWER\":1,\"USER_CANCELLED_TRIP\":1},\"count\":83},\"PUENTE PALMA\":{\"CANCELLED_DRIVER\":17,\"DONE\":54,\"CANCELLED_CLIENT\":27,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":24,\"USER_IS_NOT_HERE\":3,\"BRING_PET\":1,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":6},\"count\":98},\"SAN JOAQUIN\":{\"CANCELLED_DRIVER\":10,\"DONE\":21,\"CANCELLED_CLIENT\":10,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"MECHANICAL_FAILURE\":1,\"IVR\":7,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":3,\"ASSIGNED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":3,\"CONGESTION_ON_THE_ROAD\":3},\"count\":41},\"VILLA NUEVA\":{\"CANCELLED_DRIVER\":7,\"DONE\":9,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":1,\"TRIP_NOTIFICATION_TTL\":1,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":1},\"count\":18},\"LOS ALCAZARES\":{\"CANCELLED_DRIVER\":1,\"DONE\":1,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":1,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":1},\"count\":4},\"SAN PASCUAL\":{\"CANCELLED_DRIVER\":5,\"DONE\":7,\"cancellationCauses\":{\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":3},\"count\":12},\"CIUDAD DEL CAMPO\":{\"DONE\":1,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1},\"count\":2},\"DEPARTAMENTAL\":{\"CANCELLED_DRIVER\":61,\"DONE\":197,\"CANCELLED_CLIENT\":25,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"MECHANICAL_FAILURE\":4,\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":20,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":6,\"ASSIGNED_TIMEOUT\":4,\"TRIP_NOTIFICATION_TTL\":3,\"BRING_PET\":2,\"CONGESTION_ON_THE_ROAD\":24,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":7},\"count\":283},\"FLORALIA\":{\"CANCELLED_DRIVER\":15,\"DONE\":9,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":2,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":6,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":1},\"count\":27},\"GRATAMIRA\":{\"CANCELLED_DRIVER\":11,\"DONE\":28,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"IVR\":5,\"USER_IS_NOT_HERE\":3,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":2},\"count\":45},\"ALFONSO LOPEZ\":{\"CANCELLED_DRIVER\":9,\"DONE\":12,\"CANCELLED_CLIENT\":8,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":5,\"INVALID_ADDRESS\":1,\"ARRIVED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":2,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DOESNT_ANSWER\":1,\"USER_CANCELLED_TRIP\":1},\"count\":29},\"LOS ANDES\":{\"CANCELLED_DRIVER\":10,\"DONE\":42,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"IVR\":2,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":1,\"ASSIGNED_TIMEOUT\":1,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":3},\"count\":55},\"SANTA MONICA NORTE\":{\"CANCELLED_DRIVER\":11,\"DONE\":28,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":2,\"USER_IS_NOT_HERE\":5,\"USER_DOESNT_ANSWER\":3},\"count\":41},\"JUMBO 1\":{\"CANCELLED_DRIVER\":8,\"DONE\":20,\"CANCELLED_CLIENT\":12,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"MECHANICAL_FAILURE\":1,\"IVR\":9,\"INVALID_ADDRESS\":1,\"TRIP_NOTIFICATION_TTL\":3,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":1},\"count\":40},\"LAS CEIBAS\":{\"CANCELLED_DRIVER\":4,\"DONE\":15,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"IVR\":3,\"TRIP_NOTIFICATION_TTL\":1,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":1},\"count\":23},\"SANTA ELENA\":{\"CANCELLED_DRIVER\":26,\"DONE\":56,\"CANCELLED_CLIENT\":13,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"MECHANICAL_FAILURE\":2,\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":11,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":2,\"TRIP_NOTIFICATION_TTL\":1,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":9,\"USER_DOESNT_ANSWER\":4},\"count\":95},\"CHAPINERO\":{\"CANCELLED_DRIVER\":5,\"DONE\":16,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":22},\"CIUDAD CORDOBA\":{\"CANCELLED_DRIVER\":18,\"CANCELLED_OPERATOR\":1,\"DONE\":32,\"CANCELLED_CLIENT\":17,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":15,\"INVALID_ADDRESS\":1,\"PRE_ASSIGNED_TIMEOUT\":1,\"BRING_PET\":2,\"TRIP_NOTIFICATION_TTL\":2,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":6},\"count\":68},\"LIBERTADORES\":{\"CANCELLED_DRIVER\":19,\"DONE\":43,\"CANCELLED_CLIENT\":11,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":9,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":7,\"CONGESTION_ON_THE_ROAD\":5,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":4},\"count\":73},\"LAS AMERICAS\":{\"CANCELLED_DRIVER\":2,\"DONE\":23,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"IVR\":2,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_CANCELLED_TRIP\":1},\"count\":28},\"SAN MARCOS\":{\"DONE\":2,\"count\":2},\"PRADOS DE ORIENTE\":{\"CANCELLED_DRIVER\":22,\"DONE\":37,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":4,\"DRUNK_USER\":1,\"IVR\":3,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":8,\"USER_DOESNT_ANSWER\":3},\"count\":62},\"LAUREANO GOMEZ\":{\"CANCELLED_DRIVER\":2,\"DONE\":6,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":2,\"USER_DOESNT_ANSWER\":1},\"count\":10},\"QUINTAS DE DON SIMON\":{\"CANCELLED_DRIVER\":25,\"DONE\":52,\"CANCELLED_CLIENT\":10,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":6,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":8,\"ASSIGNED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":2,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":1},\"count\":87},\"CALICANTO\":{\"CANCELLED_DRIVER\":3,\"DONE\":5,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"USER_IS_NOT_HERE\":2,\"USER_DOESNT_ANSWER\":1},\"count\":9},\"VILLA DEL SUR\":{\"CANCELLED_DRIVER\":12,\"CANCELLED_OPERATOR\":1,\"DONE\":19,\"CANCELLED_CLIENT\":12,\"cancellationCauses\":{\"IVR\":12,\"PRE_ASSIGNED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":6,\"USER_DOESNT_ANSWER\":3},\"count\":44},\"VERSALLES - CLINICA SANTIAGO\":{\"CANCELLED_DRIVER\":6,\"DONE\":15,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"IVR\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":1},\"count\":22},\"ALAMOS\":{\"CANCELLED_DRIVER\":2,\"DONE\":5,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"IVR\":3,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":1},\"count\":11},\"SAN JUDAS TADEO\":{\"CANCELLED_DRIVER\":13,\"DONE\":31,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"USER_DISSATISFIED_VEHICLE\":1,\"MECHANICAL_FAILURE\":1,\"IVR\":5,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":2,\"ASSIGNED_TIMEOUT\":2,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":2},\"count\":50},\"ALTO MENGA\":{\"CANCELLED_DRIVER\":2,\"DONE\":1,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"USER_IS_NOT_HERE\":1,\"USER_DID_NOT_WAIT\":1},\"count\":4},\"LA HACIENDA\":{\"CANCELLED_DRIVER\":5,\"DONE\":8,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":3,\"CONGESTION_ON_THE_ROAD\":1},\"count\":16},\"SAN LUISITO\":{\"CANCELLED_DRIVER\":1,\"DONE\":1,\"cancellationCauses\":{\"CONGESTION_ON_THE_ROAD\":1},\"count\":2},\"TEJARES DE SALOMIA\":{\"DONE\":3,\"count\":3},\"METROPOLITANO DEL NORTE\":{\"CANCELLED_DRIVER\":2,\"DONE\":5,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"USER_DOESNT_ANSWER\":1},\"count\":8},\"OBRERO\":{\"CANCELLED_DRIVER\":10,\"DONE\":41,\"CANCELLED_CLIENT\":7,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":4,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":2,\"TRIP_NOTIFICATION_TTL\":2,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":3},\"count\":58},\"CENTRALIA\":{\"CANCELLED_DRIVER\":1,\"DONE\":1,\"count\":2},\"EL RODEO\":{\"CANCELLED_DRIVER\":22,\"DONE\":42,\"CANCELLED_CLIENT\":9,\"cancellationCauses\":{\"DRUNK_USER\":1,\"VEHICLE_FROM_OTHER_COMPANY\":2,\"MECHANICAL_FAILURE\":2,\"IVR\":8,\"INVALID_ADDRESS\":1,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":1,\"TRIP_NOTIFICATION_TTL\":1,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":5,\"USER_DOESNT_ANSWER\":8},\"count\":73},\"ULPIANO LLOREDA\":{\"CANCELLED_DRIVER\":4,\"DONE\":18,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":1},\"count\":23},\"MOJICA\":{\"CANCELLED_DRIVER\":5,\"DONE\":3,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"IVR\":3,\"ASSIGNED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":2},\"count\":12},\"TEMPLETE\":{\"CANCELLED_DRIVER\":18,\"DONE\":30,\"CANCELLED_CLIENT\":10,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":3,\"IVR\":8,\"USER_IS_NOT_HERE\":4,\"ASSIGNED_TIMEOUT\":1,\"BRING_PET\":1,\"TRIP_NOTIFICATION_TTL\":2,\"CONGESTION_ON_THE_ROAD\":5,\"USER_DOESNT_ANSWER\":1},\"count\":58},\"CENTRO\":{\"CANCELLED_DRIVER\":31,\"DONE\":32,\"CANCELLED_CLIENT\":21,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":16,\"USER_IS_NOT_HERE\":4,\"TRIP_NOTIFICATION_TTL\":5,\"CONGESTION_ON_THE_ROAD\":14,\"USER_DOESNT_ANSWER\":3},\"count\":84},\"EL VERGEL\":{\"CANCELLED_DRIVER\":8,\"DONE\":9,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"IVR\":3,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":4},\"count\":20},\"SAN LUIS\":{\"CANCELLED_DRIVER\":2,\"DONE\":7,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"IVR\":2,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":1},\"count\":12},\"BRISAS DE LOS ALAMOS\":{\"DONE\":5,\"count\":5},\"LA FLORA - VIZCAYA\":{\"CANCELLED_DRIVER\":2,\"cancellationCauses\":{\"USER_IS_NOT_HERE\":1},\"count\":2},\"GRANADA\":{\"CANCELLED_DRIVER\":28,\"DONE\":81,\"CANCELLED_CLIENT\":11,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":3,\"DRUNK_USER\":2,\"MECHANICAL_FAILURE\":2,\"DIFFERENT_TRIP_SERVICE\":1,\"IVR\":10,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":3,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DOESNT_ANSWER\":6},\"count\":120},\"MARIO CORREA\":{\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"TRIP_NOTIFICATION_TTL\":1},\"count\":1},\"NUEVA FLORESTA\":{\"CANCELLED_DRIVER\":14,\"DONE\":42,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"VEHICLE_FROM_OTHER_COMPANY\":4,\"HEAVY_TRAFFIC\":1,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":3,\"USER_DID_NOT_WAIT\":1,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":3},\"count\":59},\"CAPRI\":{\"CANCELLED_DRIVER\":24,\"DONE\":91,\"CANCELLED_CLIENT\":20,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"MECHANICAL_FAILURE\":1,\"IVR\":18,\"DIFFERENT_TRIP_SERVICE\":1,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":4,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DOESNT_ANSWER\":3},\"count\":135},\"FLORESTA VIEJA\":{\"CANCELLED_DRIVER\":11,\"DONE\":17,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"IVR\":1,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":4,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":1},\"count\":29},\"NUEVA TEQUENDAMA\":{\"CANCELLED_DRIVER\":62,\"DONE\":95,\"CANCELLED_CLIENT\":29,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":6,\"MECHANICAL_FAILURE\":3,\"IVR\":20,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":6,\"TRIP_NOTIFICATION_TTL\":8,\"ASSIGNED_TIMEOUT\":4,\"CONGESTION_ON_THE_ROAD\":29,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":4},\"count\":186},\"CANEY\":{\"CANCELLED_DRIVER\":39,\"DONE\":67,\"CANCELLED_CLIENT\":34,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"VEHICLE_FROM_OTHER_COMPANY\":2,\"MECHANICAL_FAILURE\":2,\"IVR\":25,\"INVALID_ADDRESS\":4,\"ARRIVED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":7,\"USER_IS_NOT_HERE\":6,\"ASSIGNED_TIMEOUT\":1,\"ADDRESS_NOT_VALID\":1,\"CONGESTION_ON_THE_ROAD\":10,\"USER_DOESNT_ANSWER\":5},\"count\":140},\"CAÑAVERALES\":{\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"IVR\":1},\"count\":2},\"OLIMPICO\":{\"CANCELLED_DRIVER\":10,\"DONE\":33,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":3,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":5,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":2},\"count\":47},\"SUCRE\":{\"CANCELLED_DRIVER\":9,\"DONE\":13,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":3,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":3,\"CONGESTION_ON_THE_ROAD\":4},\"count\":25},\"SANTA MONICA ALTA\":{\"CANCELLED_DRIVER\":3,\"DONE\":6,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":1},\"count\":10},\"LA MERCED\":{\"CANCELLED_DRIVER\":13,\"DONE\":14,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"IVR\":1,\"INVALID_ADDRESS\":1,\"BRING_PET\":1,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":3,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DOESNT_ANSWER\":1},\"count\":29},\"SANTA ISABEL\":{\"CANCELLED_DRIVER\":24,\"DONE\":26,\"CANCELLED_CLIENT\":7,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":3,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":4,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":2,\"BRING_PET\":1,\"TRIP_NOTIFICATION_TTL\":3,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":8,\"USER_DOESNT_ANSWER\":2},\"count\":57},\"VALLE DE LA FERREIRA\":{\"CANCELLED_DRIVER\":19,\"DONE\":25,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":3,\"IVR\":6,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":3,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DOESNT_ANSWER\":2},\"count\":50},\"PRIMAVERA\":{\"CANCELLED_DRIVER\":2,\"DONE\":1,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_CANCELLED_TRIP\":1},\"count\":4},\"7 DE AGOSTO\":{\"CANCELLED_DRIVER\":9,\"DONE\":27,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":3,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":2,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":5},\"count\":42},\"ANDRES SANIN\":{\"CANCELLED_DRIVER\":6,\"DONE\":9,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"IVR\":1,\"TRIP_NOTIFICATION_TTL\":1,\"USER_IS_NOT_HERE\":1,\"USER_DID_NOT_WAIT\":1,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":1},\"count\":18},\"LA BASE\":{\"CANCELLED_DRIVER\":19,\"DONE\":52,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":1,\"INVALID_ADDRESS\":2,\"USER_IS_NOT_HERE\":2,\"USER_DID_NOT_WAIT\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":4},\"count\":73},\"PARQUE INDUSTRIAL LAS DELICIAS\":{\"CANCELLED_DRIVER\":2,\"DONE\":3,\"cancellationCauses\":{\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":1},\"count\":5},\"LA RIVERA\":{\"CANCELLED_DRIVER\":5,\"DONE\":12,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":2,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":1},\"count\":19},\"SILOE\":{\"DONE\":2,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":1,\"USER_DID_NOT_WAIT\":1},\"count\":4},\"VILLADEPAL\":{\"DONE\":1,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":2},\"count\":3},\"ALFONSO BONILLA\":{\"CANCELLED_DRIVER\":3,\"DONE\":5,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":9},\"EL LIMONAR\":{\"CANCELLED_DRIVER\":74,\"DONE\":114,\"CANCELLED_CLIENT\":46,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":2,\"MECHANICAL_FAILURE\":3,\"IVR\":34,\"DIFFERENT_TRIP_SERVICE\":1,\"BRING_PET\":1,\"TRIP_NOTIFICATION_TTL\":5,\"CONGESTION_ON_THE_ROAD\":32,\"USER_DOESNT_ANSWER\":7,\"VEHICLE_FROM_OTHER_COMPANY\":5,\"INVALID_ADDRESS\":2,\"ARRIVED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":9,\"ASSIGNED_TIMEOUT\":2,\"USER_DID_NOT_WAIT\":4},\"count\":234},\"CAMINO REAL\":{\"CANCELLED_DRIVER\":25,\"DONE\":46,\"CANCELLED_CLIENT\":16,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"MECHANICAL_FAILURE\":1,\"IVR\":12,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":2,\"TRIP_NOTIFICATION_TTL\":3,\"USER_IS_NOT_HERE\":1,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":14,\"USER_DOESNT_ANSWER\":3},\"count\":87},\"ENV\":{\"CANCELLED_DRIVER\":2,\"DONE\":4,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"ARRIVED_TIMEOUT\":1},\"count\":6},\"VILLA DEL LAGO\":{\"CANCELLED_DRIVER\":6,\"DONE\":7,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":3,\"IVR\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":14},\"LEON XIII\":{\"CANCELLED_DRIVER\":15,\"DONE\":50,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"DRUNK_USER\":1,\"MECHANICAL_FAILURE\":3,\"IVR\":2,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":2,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":3},\"count\":68},\"EL PARAISO\":{\"CANCELLED_DRIVER\":9,\"CANCELLED_OPERATOR\":1,\"DONE\":22,\"CANCELLED_CLIENT\":4,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"IVR\":4,\"INVALID_ADDRESS\":2,\"PRE_ASSIGNED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":3,\"CONGESTION_ON_THE_ROAD\":2},\"count\":36},\"SALOMIA\":{\"CANCELLED_DRIVER\":14,\"DONE\":38,\"CANCELLED_CLIENT\":5,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"MECHANICAL_FAILURE\":1,\"IVR\":3,\"INVALID_ADDRESS\":1,\"ARRIVED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":1,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":2},\"count\":57},\"EL INGENIO\":{\"CANCELLED_DRIVER\":62,\"DONE\":183,\"CANCELLED_CLIENT\":20,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"DIFFERENT_TRIP_SERVICE\":1,\"IVR\":16,\"TRIP_NOTIFICATION_TTL\":3,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":9,\"USER_DOESNT_ANSWER\":9,\"VEHICLE_FROM_OTHER_COMPANY\":5,\"DRUNK_USER\":2,\"ARRIVED_TIMEOUT\":1,\"ASSIGNED_TIMEOUT\":3,\"USER_IS_NOT_HERE\":15,\"USER_DID_NOT_WAIT\":1},\"count\":265},\"MIRAFLORES\":{\"CANCELLED_DRIVER\":34,\"DONE\":87,\"CANCELLED_CLIENT\":20,\"cancellationCauses\":{\"DRUNK_USER\":1,\"IVR\":17,\"INVALID_ADDRESS\":2,\"ARRIVED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":7,\"TRIP_NOTIFICATION_TTL\":1,\"ASSIGNED_TIMEOUT\":2,\"CONGESTION_ON_THE_ROAD\":16,\"USER_DID_NOT_WAIT\":2,\"USER_DOESNT_ANSWER\":2},\"count\":141},\"VIPASA\":{\"CANCELLED_DRIVER\":8,\"DONE\":18,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"IVR\":1,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":3,\"USER_DOESNT_ANSWER\":1},\"count\":28},\"FENALCO\":{\"DONE\":2,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"TRIP_NOTIFICATION_TTL\":1},\"count\":3},\"ALTO MELENDEZ\":{\"CANCELLED_DRIVER\":5,\"DONE\":2,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":2,\"INVALID_ADDRESS\":1,\"ASSIGNED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":2},\"count\":9},\"SANTA FE\":{\"CANCELLED_DRIVER\":23,\"DONE\":76,\"CANCELLED_CLIENT\":11,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":3,\"DRUNK_USER\":1,\"IVR\":5,\"USER_DOESNT_REQUIRE_VEHICLE\":2,\"HEAVY_TRAFFIC\":1,\"USER_IS_NOT_HERE\":7,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DOESNT_ANSWER\":4},\"count\":110},\"SANTA RITA\":{\"CANCELLED_DRIVER\":4,\"DONE\":4,\"CANCELLED_CLIENT\":35,\"cancellationCauses\":{\"IVR\":20,\"ARRIVED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":14,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":1,\"USER_DOESNT_ANSWER\":1},\"count\":43},\"LOS CAMBULOS\":{\"CANCELLED_DRIVER\":22,\"DONE\":49,\"CANCELLED_CLIENT\":12,\"cancellationCauses\":{\"DRUNK_USER\":1,\"IVR\":8,\"SERVICE_CODE\":1,\"USER_IS_NOT_HERE\":8,\"TRIP_NOTIFICATION_TTL\":4,\"CONGESTION_ON_THE_ROAD\":6,\"USER_DOESNT_ANSWER\":2},\"count\":83},\"12 DE OCTUBRE\":{\"CANCELLED_DRIVER\":5,\"DONE\":15,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"IVR\":2,\"ARRIVED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":4,\"USER_CANCELLED_TRIP\":1},\"count\":23},\"NORMANDIA\":{\"CANCELLED_DRIVER\":21,\"DONE\":30,\"CANCELLED_CLIENT\":19,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":2,\"MECHANICAL_FAILURE\":2,\"IVR\":16,\"ASSIGNED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":4,\"BRING_PET\":3,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":7,\"USER_DID_NOT_WAIT\":2},\"count\":70},\"MARIANO RAMOS\":{\"CANCELLED_DRIVER\":70,\"DONE\":140,\"CANCELLED_CLIENT\":41,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":6,\"IVR\":25,\"DIFFERENT_TRIP_SERVICE\":1,\"TRIP_NOTIFICATION_TTL\":13,\"BRING_PET\":2,\"CONGESTION_ON_THE_ROAD\":20,\"USER_DOESNT_ANSWER\":10,\"VEHICLE_FROM_OTHER_COMPANY\":8,\"INVALID_ADDRESS\":3,\"USER_IS_NOT_HERE\":9,\"ASSIGNED_TIMEOUT\":3,\"ADDRESS_NOT_VALID\":1,\"USER_DID_NOT_WAIT\":2},\"count\":251},\"SIMON BOLIVAR\":{\"CANCELLED_DRIVER\":13,\"DONE\":12,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"IVR\":2,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":3,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DOESNT_ANSWER\":3},\"count\":28},\"SANTA BARBARA\":{\"CANCELLED_DRIVER\":3,\"DONE\":8,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":1,\"USER_DOESNT_ANSWER\":1},\"count\":12},\"EL BOSQUE\":{\"CANCELLED_DRIVER\":10,\"DONE\":13,\"CANCELLED_CLIENT\":6,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":3,\"INVALID_ADDRESS\":1,\"TRIP_NOTIFICATION_TTL\":2,\"USER_IS_NOT_HERE\":1,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DOESNT_ANSWER\":3},\"count\":29},\"POPULAR\":{\"CANCELLED_DRIVER\":6,\"DONE\":24,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":2,\"BRING_PET\":1,\"CONGESTION_ON_THE_ROAD\":1,\"USER_DOESNT_ANSWER\":1},\"count\":32},\"TEQUENDAMA\":{\"CANCELLED_DRIVER\":45,\"DONE\":86,\"CANCELLED_CLIENT\":29,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"MECHANICAL_FAILURE\":2,\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":24,\"INVALID_ADDRESS\":3,\"ASSIGNED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":9,\"BRING_PET\":1,\"TRIP_NOTIFICATION_TTL\":3,\"USER_DID_NOT_WAIT\":1,\"CONGESTION_ON_THE_ROAD\":17,\"USER_DOESNT_ANSWER\":4},\"count\":160},\"GUAYAQUIL\":{\"CANCELLED_DRIVER\":19,\"DONE\":40,\"CANCELLED_CLIENT\":12,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":3,\"IVR\":8,\"ASSIGNED_TIMEOUT\":1,\"USER_IS_NOT_HERE\":3,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":8,\"USER_DID_NOT_WAIT\":2,\"USER_DOESNT_ANSWER\":1},\"count\":71},\"SAN VICENTE\":{\"CANCELLED_DRIVER\":14,\"DONE\":20,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"DRUNK_USER\":1,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":1,\"USER_IS_NOT_HERE\":1,\"BRING_PET\":2,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":7},\"count\":35},\"MORICHAL\":{\"CANCELLED_DRIVER\":8,\"DONE\":14,\"CANCELLED_CLIENT\":5,\"cancellationCauses\":{\"MECHANICAL_FAILURE\":1,\"IVR\":3,\"INVALID_ADDRESS\":1,\"ARRIVED_TIMEOUT\":1,\"TRIP_NOTIFICATION_TTL\":2,\"USER_IS_NOT_HERE\":1,\"ASSIGNED_TIMEOUT\":1,\"USER_DOESNT_ANSWER\":2},\"count\":27},\"JUNIN\":{\"CANCELLED_DRIVER\":18,\"DONE\":91,\"CANCELLED_CLIENT\":8,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"MECHANICAL_FAILURE\":1,\"DIFFERENT_TRIP_SERVICE\":1,\"IVR\":6,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":6,\"CONGESTION_ON_THE_ROAD\":4,\"USER_DID_NOT_WAIT\":1,\"USER_DOESNT_ANSWER\":3},\"count\":117},\"SINDICAL\":{\"CANCELLED_DRIVER\":2,\"DONE\":5,\"CANCELLED_CLIENT\":3,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"MECHANICAL_FAILURE\":1,\"USER_IS_NOT_HERE\":2,\"USER_DOESNT_ANSWER\":1},\"count\":10},\"MANZANARES\":{\"CANCELLED_DRIVER\":19,\"DONE\":45,\"CANCELLED_CLIENT\":5,\"cancellationCauses\":{\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":5,\"ASSIGNED_TIMEOUT\":3,\"USER_IS_NOT_HERE\":2,\"CONGESTION_ON_THE_ROAD\":6,\"USER_DOESNT_ANSWER\":4},\"count\":69},\"FLORA INDUSTRIAL\":{\"CANCELLED_DRIVER\":4,\"DONE\":4,\"CANCELLED_CLIENT\":2,\"cancellationCauses\":{\"IVR\":2,\"USER_IS_NOT_HERE\":2},\"count\":10},\"PRADOS DEL LIMONAR\":{\"CANCELLED_DRIVER\":13,\"DONE\":35,\"CANCELLED_CLIENT\":14,\"cancellationCauses\":{\"TRIP_CANCELLED_DELAY\":1,\"VEHICLE_FROM_OTHER_COMPANY\":1,\"IVR\":13,\"ARRIVED_TIMEOUT\":2,\"USER_IS_NOT_HERE\":1,\"ASSIGNED_TIMEOUT\":1,\"CONGESTION_ON_THE_ROAD\":6,\"USER_DOESNT_ANSWER\":1},\"count\":62},\"PUERTAS DEL SOL\":{\"CANCELLED_DRIVER\":3,\"DONE\":4,\"CANCELLED_CLIENT\":1,\"cancellationCauses\":{\"IVR\":1,\"CONGESTION_ON_THE_ROAD\":2},\"count\":8},\"SAN CARLOS\":{\"CANCELLED_DRIVER\":13,\"DONE\":44,\"CANCELLED_CLIENT\":11,\"cancellationCauses\":{\"IVR\":10,\"INVALID_ADDRESS\":1,\"USER_IS_NOT_HERE\":5,\"TRIP_NOTIFICATION_TTL\":1,\"CONGESTION_ON_THE_ROAD\":2,\"USER_DOESNT_ANSWER\":2},\"count\":68}},\"platforms\":{\"EMI\":{\"DRIVER\":{\"count\":1400,\"REQUESTED\":{\"CANCELLED_CLIENT\":{\"count\":63,\"timeTendency\":{\"median\":300,\"mean\":271.1763888888889,\"range\":[12,305]}},\"ASSIGNED\":{\"CANCELLED_DRIVER\":{\"count\":423,\"timeTendency\":{\"median\":40,\"mean\":141.86282666207188,\"range\":[1,1204]}},\"CANCELLED_CLIENT\":{\"count\":10,\"timeTendency\":{\"median\":93.5,\"mean\":106.58333333333333,\"range\":[12,420]}},\"ARRIVED\":{\"ON_BOARD\":{\"DONE\":{\"count\":708,\"timeTendency\":{\"median\":177.25,\"mean\":450.24523663042305,\"range\":[0,2666]}},\"count\":745,\"timeTendency\":{\"median\":24.5,\"mean\":56.899723958774494,\"range\":[1,922]},\"TERMINATED_SYSTEM\":{\"DONE\":{\"count\":37,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":37,\"timeTendency\":{\"median\":2735.5,\"mean\":2734.8821428571428,\"range\":[2702,2759]}}},\"CANCELLED_DRIVER\":{\"count\":143,\"timeTendency\":{\"median\":76.5,\"mean\":122.03857560568088,\"range\":[2,1117]}},\"count\":892,\"timeTendency\":{\"median\":227.5,\"mean\":241.31623131201854,\"range\":[1,1103]},\"TERMINATED_SYSTEM\":{\"CANCELLED_DRIVER\":{\"count\":4,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":4,\"timeTendency\":{\"median\":1225.5,\"mean\":1221,\"range\":[1200,1233]}}},\"count\":1337,\"timeTendency\":{\"median\":7,\"mean\":18.422383858349843,\"range\":[0,295]},\"TERMINATED_SYSTEM\":{\"CANCELLED_DRIVER\":{\"count\":12,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":12,\"timeTendency\":{\"median\":1227.25,\"mean\":1228.5625,\"range\":[1205,1259]}}},\"count\":1400,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}}}},\"IVR\":{\"DRIVER\":{\"count\":8226,\"REQUESTED\":{\"CANCELLED_CLIENT\":{\"count\":1286,\"timeTendency\":{\"median\":287,\"mean\":287.97833640654886,\"range\":[224,300]}},\"PRE_ASSIGNED\":{\"CANCELLED_CLIENT\":{\"count\":13,\"timeTendency\":{\"median\":36,\"mean\":97.16666666666667,\"range\":[0,636]}},\"ASSIGNED\":{\"CANCELLED_CLIENT\":{\"count\":100,\"timeTendency\":{\"median\":350,\"mean\":410.9294312169312,\"range\":[6,1136]}},\"ARRIVED\":{\"DONE\":{\"count\":394,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":394,\"timeTendency\":{\"median\":269,\"mean\":290.7075870384099,\"range\":[2,1203]}},\"count\":558,\"timeTendency\":{\"median\":37.5,\"mean\":88.58924600963063,\"range\":[4,1140]},\"TERMINATED_SYSTEM\":{\"CANCELLED_DRIVER\":{\"count\":64,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":64,\"timeTendency\":{\"median\":1229.5,\"mean\":1227.5278711484593,\"range\":[1200,1259]}}},\"count\":576,\"timeTendency\":{\"median\":44.75,\"mean\":70.84115857011254,\"range\":[10,290]},\"TERMINATED_SYSTEM\":{\"CANCELLED_OPERATOR\":{\"count\":5,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":5,\"timeTendency\":{\"median\":1232,\"mean\":1231.2222222222224,\"range\":[1217,1254]}}},\"ASSIGNED\":{\"CANCELLED_DRIVER\":{\"count\":1354,\"timeTendency\":{\"median\":75.5,\"mean\":209.7230494779396,\"range\":[2,1176]}},\"CANCELLED_CLIENT\":{\"count\":1,\"timeTendency\":{\"median\":82,\"mean\":82,\"range\":[82,82]}},\"ARRIVED\":{\"ON_BOARD\":{\"DONE\":{\"count\":4182,\"timeTendency\":{\"median\":225,\"mean\":447.80790231551987,\"range\":[0,2743]}},\"count\":4444,\"timeTendency\":{\"median\":25.75,\"mean\":55.99264791237829,\"range\":[1,1198]},\"TERMINATED_SYSTEM\":{\"DONE\":{\"count\":262,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":262,\"timeTendency\":{\"median\":2729,\"mean\":2730.583752012883,\"range\":[2700,2759]}}},\"CANCELLED_DRIVER\":{\"count\":471,\"timeTendency\":{\"median\":84.5,\"mean\":151.087448229817,\"range\":[0,1222]}},\"CANCELLED_CLIENT\":{\"count\":2,\"timeTendency\":{\"median\":377.5,\"mean\":377.5,\"range\":[298,457]}},\"count\":4951,\"timeTendency\":{\"median\":217.5,\"mean\":237.9194813391678,\"range\":[1,1246]},\"TERMINATED_SYSTEM\":{\"CANCELLED_DRIVER\":{\"count\":34,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":34,\"timeTendency\":{\"median\":1230.5,\"mean\":1231.3511904761906,\"range\":[1206,1257]}}},\"count\":6364,\"timeTendency\":{\"median\":3.5,\"mean\":7.555207842954219,\"range\":[0,66]},\"TERMINATED_SYSTEM\":{\"CANCELLED_DRIVER\":{\"count\":58,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":58,\"timeTendency\":{\"median\":1220.5,\"mean\":1223.8256172839508,\"range\":[1200,1259]}}},\"count\":8226,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}}}},\"BOOKER\":{\"DRIVER\":{\"count\":39,\"REQUESTED\":{\"CANCELLED_CLIENT\":{\"count\":2,\"timeTendency\":{\"median\":29,\"mean\":29,\"range\":[12,46]}},\"ASSIGNED\":{\"CANCELLED_DRIVER\":{\"count\":9,\"timeTendency\":{\"median\":39,\"mean\":90.28571428571429,\"range\":[8,349]}},\"CANCELLED_CLIENT\":{\"count\":6,\"timeTendency\":{\"median\":240.5,\"mean\":286.5,\"range\":[8,494]}},\"ARRIVED\":{\"ON_BOARD\":{\"DONE\":{\"count\":17,\"timeTendency\":{\"median\":6,\"mean\":251.60606060606062,\"range\":[0,1950]}},\"count\":21,\"timeTendency\":{\"median\":1.5,\"mean\":14.511904761904763,\"range\":[0,85]},\"TERMINATED_SYSTEM\":{\"DONE\":{\"count\":4,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}},\"count\":4,\"timeTendency\":{\"median\":2720.5,\"mean\":2721,\"range\":[2703,2740]}}},\"CANCELLED_DRIVER\":{\"count\":1,\"timeTendency\":{\"median\":4,\"mean\":4,\"range\":[4,4]}},\"count\":22,\"timeTendency\":{\"median\":226,\"mean\":227.65555555555557,\"range\":[6,409]}},\"count\":37,\"timeTendency\":{\"median\":7.5,\"mean\":10.332407407407409,\"range\":[1,211]}},\"count\":39,\"timeTendency\":{\"median\":0,\"mean\":0,\"range\":[0,0]}}}}}}";
//        System.out.println(new JSONObject(jsonTest));
//        Calendar ca1 = Calendar.getInstance();
//
//        ca1.set(2017, 3, 27, 6, 20);
//
//        int wk = ca1.get(Calendar.WEEK_OF_MONTH);
//        System.out.println("Week of Month :" + wk);
//        String text = "co.com.nebulae.reporter.report-threshold-hour";
//        TestLambda lambda = new TestLambda();
//        lambda.test(text);
//        
//        int batchSize = 10;
//        List<String> sessionIdList = new ArrayList<>();
//        
//        for (int i = 0; i < 50; i++) {
//            sessionIdList.add("test"+i);
//            
//        }
//        
//                IntStream.range(0, (sessionIdList.size() + batchSize - 1) / batchSize)
//                .mapToObj(i -> sessionIdList.subList(i * batchSize, Math.min(sessionIdList.size(), (i + 1) * batchSize)))
//                .forEach(data -> {
//                    System.out.println("TANDA");
//                    System.out.println("data -> "+ Arrays.toString(data.toArray()));
//                    System.out.println("FIN");
//                });
//        
//        TestLambda lambda = new TestLambda();
//        lambda.sumValues();
    }

}
