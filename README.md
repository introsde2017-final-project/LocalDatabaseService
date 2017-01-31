# introsde2017-final-project
# Local Database Service
**Final project | University of Trento**

Documentation about the Local Database Service: SOAP Web Service

## API
#### Get all people
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:getPeopleList>
        </m:getPeopleList>
    </soap:Body>
</soap:Envelope>
```

#### Read one person
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:readPerson>
            <personId>1</personId>
        </m:readPerson>
    </soap:Body>
</soap:Envelope>
```

#### Update person (no measure)
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:updatePerson>
            <person>
                <birthdate>17/07/1964</birthdate>
                <email>pallino.panco@gmail.com</email>
                <firstname>Pallino</firstname>
                <idPerson>2</idPerson>
                <lastname>Pinco</lastname>
            </person>
        </m:updatePerson>
    </soap:Body>
</soap:Envelope>
```

#### Create person
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:createPerson>
            <person>
                <birthdate>02/01/1963</birthdate>
                <currentHealth>
                    <measure>
                    <dateRegistered>20/05/2015</dateRegistered>
                    <measureType>weight</measureType>
                    <measureValue>60</measureValue>
                    <measureValueType>double</measureValueType>
                </measure>
                <measure>
                    <dateRegistered>11/10/2016</dateRegistered>
                    <measureType>height</measureType>
                    <measureValue>156</measureValue>
                    <measureValueType>integer</measureValueType>
                </measure>
                </currentHealth>
                <email>me@gmail.com</email>
                <firstname>John</firstname>
                <lastname>Fidge</lastname>
            </person>
        </m:createPerson>
    </soap:Body>
</soap:Envelope>
```

#### Delete person
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:deletePerson>
            <personId>20</personId>
        </m:deletePerson>
    </soap:Body>
</soap:Envelope>
```

#### Get person by chat id
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:getPersonByChatId>
            <chatId>50000</chatId>
        </m:getPersonByChatId>
    </soap:Body>
</soap:Envelope>
```

#### Read person history
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:readPersonHistory>
            <personId>1</personId>
            <measureType>weight</measureType>
        </m:readPersonHistory>
    </soap:Body>
</soap:Envelope>
```

#### Read all measures
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:readMeasureTypes>
        </m:readMeasureTypes>
    </soap:Body>
</soap:Envelope>
```

#### Read measure by person id, type and mid
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:readPersonMeasure>
            <personId>1</personId>
            <measureId>1</measureId>
            <measureType>weight</measureType>
        </m:readPersonMeasure>
    </soap:Body>
</soap:Envelope>
```

#### Save person measure
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:savePersonMeasure>
            <personId>1</personId>
            <measure>
                <dateRegistered>23/05/2016</dateRegistered>
                <measureType>height</measureType>
                <measureValue>140</measureValue>
                <measureValueType>integer</measureValueType>
            </measure>
        </m:savePersonMeasure>
    </soap:Body>
</soap:Envelope>
```

#### Update measure
POST http://127.0.1.1:6902/soap/people
```
<soap:Envelope
xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
soap:encodingStyle="http://www.w3.org/2001/12/soap-encoding">
    <soap:Body xmlns:m="http://soap.localdatabase.introsde/">
        <m:updatePersonMeasure>
            <personId>1</personId>
            <measure>
                <idMeasure>457</idMeasure>
                <dateRegistered>23/05/2016</dateRegistered>
                <measureType>height</measureType>
                <measureValue>140</measureValue>
                <measureValueType>integer</measureValueType>
            </measure>
        </m:updatePersonMeasure>
    </soap:Body>
</soap:Envelope>
```
