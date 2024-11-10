export const formatCcNumber = (ccnumber: string): string => {
    return `**** **** **** ${ccnumber.slice(-4)}`;
};