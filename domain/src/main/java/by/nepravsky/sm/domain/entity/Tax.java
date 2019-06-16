package by.nepravsky.sm.domain.entity;

public class Tax {

    private double brokersFee;
    private double selesTax;
    private double reactionTax;

    public Tax(double brokersFee, double selesTax, double reactionTax) {
        this.brokersFee = brokersFee / 100 + 1;
        this.selesTax = selesTax / 100 + 1;
        this.reactionTax = reactionTax / 100 +1;
    }

    public double getBrokersFee() {
        return brokersFee;
    }

    public double getSelesTax() {
        return selesTax;
    }

    public double getReactionTax() {
        return reactionTax;
    }
}
