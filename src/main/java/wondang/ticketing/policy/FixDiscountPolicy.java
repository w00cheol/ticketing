package wondang.ticketing.policy;

import wondang.ticketing.domain.Member;

@MainDiscountPolicy
public class FixDiscountPolicy implements DiscountPolicy {
    private int fixDiscountBase = 0;
    private int fixDiscountStaffBenefit = 1000;

    // return discount amount
    @Override
    public int discount(Member member) {
        int discountPrice = 0;
        discountPrice += fixDiscountBase;

        if (member.getUsername().startsWith("staff.")) {
            discountPrice += fixDiscountStaffBenefit;
        }

        return discountPrice;
    }
}