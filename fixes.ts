// fixes.ts

import { removeDuplicates, isPerson, fetchData, formatDate, greetPerson, calculateAreaOfCircle } from './fixes';

// Example usage:
const numbers = [1, 2, 2, 3, 4, 4];
const uniqueNumbers = removeDuplicates(numbers);
console.log(uniqueNumbers);

const person = { name: 'Alice', age: 30 };
if (isPerson(person)) {
    console.log(greetPerson(person));
}

fetchData('https://api.example.com/data')
    .then(data => console.log(data))
    .catch(error => console.error('Error fetching data:', error));

const today = new Date();
console.log('Formatted date:', formatDate(today));

try {
    const area = calculateAreaOfCircle(5);
    console.log('Area of circle:', area);
} catch (error) {
    console.error('Error calculating area:', error);
}
