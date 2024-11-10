export interface Statistics {
    totalUsers: number;
    averageAge: number;
    mostCommonCity: string;
    ageDistribution: Record<string, number>;
}