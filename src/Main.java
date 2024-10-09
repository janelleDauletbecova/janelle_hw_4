import java.util.Random;
public class Main {
    public static int bossHealth = 500;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {300, 290, 250, 280, 400, 200, 240, 250};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 0, 0, 5};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Wicher", "Thor"};
    public static int roundNumber;
    public static int medicHeal = 150;
    public static boolean golemaAlive = true;
    public static boolean luckyAlive = true;
    public static boolean wicherAlive = true;
    public static boolean thorAlive = true;
    public static boolean bossStunned = false;


    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        if (!bossStunned) {
            bossAttacks();
        }
        bossAttacks();
        heroesAttack();
        printStatistics();
        medicHeal();
        luckyChance();
        wicherRevive();
        thorStunner();
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomInd];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage *= coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicHeal() {
        int medic = 3;
        if (heroesHealth[3] <= 0) {
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && i != medic) {
                heroesHealth[i] = heroesHealth[i] + medicHeal;
                System.out.println("Medic healed " + heroesHealth[i] + " for "+ medicHeal + " HP");

                break;
            }
        }
    }

    public static void golemDefender() {
        if (golemaAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && heroesAttackType[i] != "Golem") {
                    heroesHealth[i] = heroesHealth[i] - bossDamage / 5;
                    if (heroesHealth[i] < 0) {
                        heroesHealth[i] = 0;
                    }
                }
            }
        }
    }

    public static void luckyChance() {
        if (luckyAlive && bossDamage > 0) {
            Random random = new Random();
            boolean isLucky = random.nextBoolean();
            if (isLucky) {
                System.out.println("Lucky dodget the BOSS blows");
            }
        }
    }

    public static void wicherRevive() {
        if (wicherAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] == 0 && i != 3) {
                    heroesHealth[i] = 250;
                    wicherAlive = false;
                    System.out.println("Wicher revive " + heroesAttackType[i] + "but died");
                    break;
                }
            }
        }
    }

    public static void thorStunner() {
        if (thorAlive && !bossStunned) {
            Random random = new Random();
            boolean isStanned = random.nextBoolean();
            if (isStanned) {
                bossStunned = true;
                System.out.println("Thor stunned the BOSS for 1 round");
            }
        }
    }

    public static void bossAttacks() {
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

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}