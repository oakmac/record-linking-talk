const faker = require('faker')
const numPatients = 10
const numDuplicateMin = 1
const numDuplicateMax = 15

function randomPatient () {
  return {
    address: {
      street: faker.address.streetAddress(),
      zip: faker.address.zipCode().substr(0, 5)
    },
    dob: faker.date.between(new Date(1945, 0, 1), new Date()).toISOString().substr(0, 10),
    fname: faker.name.firstName(),
    lname: faker.name.lastName(),
    medicalRecordNumber: getRandomInt(22000, 93000) + '',
    ssn: getRandomInt(999999999, 100000000) + '',
    visitNumber: getRandomInt(243000, 915000) + ''
  }
}

function getRandomInt (min, max) {
  min = Math.ceil(min)
  max = Math.floor(max)
  return Math.floor(Math.random() * (max - min + 1)) + min
}

let patients = []
for (let i = 0; i < numPatients; i++) {
  // const newPatient = randomPatient()
  // for (let j = numDuplicateMin; j < numDuplicateMax; j++) {
  //
  // }
  patients.push(randomPatient())
}

console.log(JSON.stringify(patients, null, 2))
