const { convertJsonToObject } = require("../json-file-reader");

describe("test json-file-reader", () => {
  test("should read a json file and convert it into a javascript object", () => {
    expect(convertJsonToObject("./src/__tests__/files/simple-appointment.json")).toEqual({
      patientId: "u234982",
      doctorId: "d981652",
      appointmentDate: "2021/02/20 10:00",
    });
  });

  test("should throw an exception when file is not present", () => {
    expect(() => convertJsonToObject("malformed-$$$file")).toThrow(/no such file or directory/);
  });

  test("should throw an exception when file content is not json", () => {
    expect(() => convertJsonToObject("./src/__tests__/files/not-json.txt")).toThrow(/Unexpected token/);
  });
});
