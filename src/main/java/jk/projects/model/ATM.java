package jk.projects.model;


public class ATM {
    private int amount;
    private int fifties;
    private int twenties;
    private static ATM instance = null;

    private ATM(){}

    public void init(int amount) {
        this.amount = amount;
        this.fifties = 0;
        this.twenties = 0;
        calculateNotes(amount);
    }

    private void calculateNotes(int amount) {

        int givenHundreds = (int) (amount / 100);
        int rest = amount % 100;
        int givenTwenties = 0;
        int givenFifties = 0;


        if (givenHundreds >= 1) {
            for (int i = 1; i <= givenHundreds; i++) {
                if (i % 2 == 0)
                    givenTwenties += 5;
                else
                    givenFifties += 2;
            }
        }

        while(rest >= 20) {
            if (rest % 50 == 0) {
                givenFifties += 1;
                rest -= 50;
            }
            else if (rest % 20 == 0) {
                int noOf20s = rest /20;
                givenTwenties += noOf20s;
                rest -= noOf20s * 20;
            } else {
                if (rest > 50) {
                    int mod50 = rest % 50;
                    if (mod50 >= 20 && mod50 < 30 || mod50 >= 40 && mod50 < 50) {
                        givenFifties += 1;
                        givenTwenties += mod50 / 20;
                        rest -= 50 + (mod50 / 20) * 20;
                    } else {
                        int noOf20s = rest / 20;
                        givenTwenties += noOf20s;
                        rest -= noOf20s * 20;
                    }
                } else {
                    int noOf20s = rest / 20;
                    givenTwenties += noOf20s;
                    rest -= noOf20s * 20;
                }
            }
        }

        this.twenties = givenTwenties;
        this.fifties = givenFifties;

        this.amount = this.twenties * 20 + this.fifties * 50;
        System.out.println("--------------ATM Cash-------------");
        System.out.println("Total Amount: " + this.amount);
        System.out.println("Notes of 50s: " + this.fifties);
        System.out.println("Notes of 20s: " + this.twenties);
        System.out.println("-----------------------------------");
    }

    public int getAmount() {
        return amount;
    }

    public static ATM getInstance() {
        if (instance == null) {
            instance = new ATM();
        }
        return instance;
    }

    /**
     * This method checks whether the requested amount is a valid amount as a combination of 50 and 20 notes
     * and can be covered by the available amount of the ATM
     * @param amount
     * @return
     */
    public boolean isAmtValid(int amount) {
        boolean isValid = false;
        if (amount > this.amount)
            return false;
        if (amount % 50 == 0) {
            isValid = true;
        } else if (amount % 20 == 0) {
            isValid = true;
        } else {
            int mod50 = amount % 50;
            if (mod50 % 20 == 0)
                isValid = true;
        }
        return isValid;
    }

    /**
     * This method is called only if isAmtValid(amount) has returned true
     * @param amount
     * @param type
     * @return true if atm cash is enough and type = 1 or type = 2
     */
    public boolean checkCashAvailabilityAndUpdateAmt(int amount, int type) {
        if (amount <= this.amount || type == Transaction.DEPOSIT) {
            int givenHundreds = (int) amount / 100;
            int givenFifties = 0;
            int givenTwenties = 0;
            for (int i = 1; i <= givenHundreds; i++) {
                if (i % 2 == 0) {
                    givenTwenties += 5;
                } else {
                    givenFifties += 2;
                }
            }
            if (type == Transaction.WITHDRAW && amount == this.amount) {
                changeAmt(this.twenties, this.fifties, Transaction.WITHDRAW);
                return true;
            }

            if (amount % 50 == 0 && amount % 20 == 0) { //both i.e. find 100s and give some as 50s and some other as 20s
                if ((this.twenties >= givenTwenties && this.fifties >= givenFifties) || type == Transaction.DEPOSIT) {
                    changeAmt(givenTwenties, givenFifties, type);
                    return true;
                } else {
                    return false;
                }

            } else if (amount % 50 == 0 && amount % 20 != 0) { //50s only
                givenFifties += 1;
                if ((this.fifties >= givenFifties && this.twenties >= givenTwenties) || type == Transaction.DEPOSIT) {
                    changeAmt(givenTwenties, givenFifties, type);
                    return true;
                } else {
                    return false;
                }
            } else { //amount multiple of 20 OR amount not multiple of 20 or 50
                  int rest = amount - givenHundreds * 100;
                  if (rest < 50) {
                    givenTwenties += (rest / 20);
                  } else { // > 50 - can't be equal to 50
                      int mod50 = rest % 50;
                      if (mod50 == 20 || mod50 == 40) {
                          givenFifties += 1;
                          givenTwenties += mod50 / 20;
                      } else if (mod50 == 10 || mod50 == 30){
                          int noOf20s = rest / 20;
                          givenTwenties += noOf20s;
                      }
                  }
                if ((this.twenties >= givenTwenties && this.fifties >= givenFifties)|| type == Transaction.DEPOSIT) {
                    changeAmt(givenTwenties, givenFifties, type);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public void changeAmt(int twenties, int fifties, int type) {
        if (type == Transaction.DEPOSIT) {
            this.twenties += twenties;
            this.fifties += fifties;
        } else if (type == Transaction.WITHDRAW) {
            this.twenties -= twenties;
            this.fifties -= fifties;
        }
        this.amount = this.twenties * 20 + this.fifties * 50;
        System.out.println("-------------ATM Cash-------------");
        System.out.println("Total Amount: " + this.amount);
        System.out.println("No of 20 notes: " + this.twenties);
        System.out.println("No of 50 notes: " + this.fifties);
        System.out.println("----------------------------------");
    }

    public int getFifties() {
        return fifties;
    }

    public int getTwenties() {
        return twenties;
    }
}
