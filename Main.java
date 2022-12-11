import java.util.Random;

public class Main {
    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefence;

    public static boolean stun;
    public static int[] heroesHealth = {270, 260, 250, 300, 600, 220, 230, 200};
    public static int[] heroesDamage = {10, 20, 30, 0 ,5 ,12 ,15 ,18};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic","Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseDefence();
        bossHits();
        Medic();
        Thor();
        Lucky();
        Berserk();
        Golem();
        heroesHit();
        printStatistics();
    }
    public static void Medic() {
        int randomsHeroes = randomGenerationHero();
        int randomHealding = (int) (Math.random() * 150);
        if (heroesHealth[3] > 0) {

            while (randomsHeroes == 3) {
                randomsHeroes = randomGenerationHero();
            }
            if (heroesHealth[randomsHeroes] > 0 && heroesHealth[randomsHeroes] < 100) {
                int previosHp = heroesHealth[randomsHeroes];
                heroesHealth[randomsHeroes] += randomHealding;

                System.out.println("Медик вылечил " + heroesAttackType[randomsHeroes] + " на кол-во " + randomHealding + " Было "
                        + previosHp + " Стало " + heroesHealth[randomsHeroes]);

            }
        }
    }
    public static void Golem() {
        int dmg = bossDamage;
        int howAliveHerro = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[4] > 0 && heroesHealth[4] >=dmg * howAliveHerro) {
                if (i == 4) {
                    continue;
                } else if (heroesHealth[i] > 0 && !stun) {
                    howAliveHerro++;
                    heroesHealth[i] += dmg - (dmg / heroesHealth.length -1);
                }
                heroesHealth[4] -= dmg * howAliveHerro;
                System.out.println(" Голем поглотил: " + dmg * howAliveHerro);
                break;
            }
        }
    }
    public static void Lucky() {
        Random random = new Random();
        boolean miss = random.nextBoolean();
        if (heroesHealth[5] > 0 && miss) {
            heroesHealth[5] += bossDamage - bossDamage / heroesHealth.length;

            System.out.println("Lucky Увернулся: " + " да");
        }
    }
    public static void Berserk(){
        Random random = new Random();
        int randomResult = random.nextInt(5)+1;
        int blockedDamage = bossDamage / randomResult;
        heroesHealth[6] += blockedDamage - blockedDamage;
        bossHealth -= blockedDamage;
        System.out.println("Берсерк заблокировал и вернул урон боссу " + blockedDamage);
    }
    public static void Thor(){
        Random random = new Random();
        boolean rnd = random.nextBoolean();
        if (rnd && heroesHealth[7] > 0){
            stun = true;
            System.out.println("Thor оглушил босса");
        }

    }

    private static int randomGenerationHero(){
        return  (int) Math.floor(Math.random() * heroesAttackType.length);
    }



    public static void chooseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefence);
    }

    public static void bossHits() {
        if (!stun) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }
        else {
            stun = false;
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    int critical_damage = coeff * heroesDamage[i];
                    if (bossHealth - critical_damage < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - critical_damage;
                    }
                    System.out.println("Critical damage: " + critical_damage + " [" + coeff + "]");
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        if (roundNumber == 0) {
            System.out.println("BEFORE GAME STARTED ______________");
        } else {
            System.out.println(roundNumber + " ROUND ______________");
        }
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " [" + heroesDamage[i] + "]");
        }
    }
}