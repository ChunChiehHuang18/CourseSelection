import requests
import threading
import json
import random
import string

# url api config
local_host = "http://localhost:8081/"
url_api_student = "student/"
url_api_instructor = "instructor/"
url_api_course = "course/"
url_api_selection = "selection/"

# post data
gender = ["Male", 'Female', 'Bisexual']
class_time = ["1,2,3", "4,5,6", "6,7,8"]

"""
This is a Python version of test script for course selection project.
There's 4 class Student, Instructor, Course, Selection and related API you can use.
All the example are written in main scope and you can change the parameters if you need.
"""


class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'


class Student:

    def add_test(self, number):
        data = {"Student_Number": number, "Student_Name": "Student" + str(number), "Student_Gender": gender[number % 3]}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_student, data=json_data)
        return r.content

    def __add(self, student_number):
        data = {"Student_Name": "Student" + str(student_number), "Student_Gender": gender[student_number % 3]}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_student, data=json_data)
        print(r.content)

    def add_student_task(self, num):
        for i in range(num):
            threading.Thread(target=self.__add(i), args=(i,)).start()

    def query_all(self):
        r = requests.get(local_host + url_api_student)
        return r.content

    def query_by_number(self, student_number):
        r = requests.get((local_host + url_api_student + str(student_number)))
        return r.content

    def query_selection(self, student_number):
        r = requests.get((local_host + url_api_student + str(student_number) + '/selection'))
        return r.content


class Instructor:

    def add_test(self, number):
        data = {"Instructor_Number": number,
                "Instructor_Name": "Instructor" + str(number),
                "Instructor_Office": ''.join(random.choice(string.ascii_letters) for x in range(4))}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_instructor, data=json_data)
        return r.content

    def __add(self, instructor_number):
        data = {"Instructor_Name": "Instructor" + str(instructor_number),
                "Instructor_Office": ''.join(random.choice(string.ascii_letters) for x in range(4))}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_instructor, data=json_data)
        print(r.content)

    def add_task(self, num):
        for i in range(num):
            threading.Thread(target=self.__add(i), args=(i,)).start()

    def query_all(self):
        r = requests.get(local_host + url_api_instructor)
        return r.content

    def query_by_number(self, instructor_number):
        r = requests.get((local_host + url_api_instructor + str(instructor_number)))
        return r.content

    def query_course(self, instructor_number):
        r = requests.get((local_host + url_api_instructor + str(instructor_number) + '/course'))
        return r.content


class Course:

    def add_test(self, number):
        data = {"Course_Number": str(number).zfill(5),
                "Course_Title": "Sleep", "Course_Size": 10, "Course_Weekday": number % 5 + 1,
                "Instructor_Number": number, "Course_Classtime": class_time[number % 3]}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_course, data=json_data)
        return r.content

    def __add(self, course_number):
        data = {"Course_Number": ''.join(random.choice(string.ascii_letters) for x in range(5)),
                "Course_Title": "Sleep", "Course_Size": 10, "Course_Weekday": course_number % 5 + 1,
                "Instructor_Number": course_number + 1, "Course_Classtime": class_time[course_number % 3]}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_course, data=json_data)
        print(r.content)

    def add_task(self, num):
        for i in range(num):
            threading.Thread(target=self.__add(i), args=(i,)).start()

    def delete(self, course_number):
        data = {"Course_Number": course_number}
        json_data = json.dumps(data)
        r = requests.delete(local_host + url_api_course, data=json_data)
        return r.content

    def query_all(self):
        r = requests.get(local_host + url_api_course)
        return r.content

    def query_by_number(self, course_number):
        r = requests.get((local_host + url_api_course + str(course_number)))
        return r.content


class Selection:

    def add_test(self, number):
        data = {"Selection_Number": number, "Course_Number": str(number).zfill(5), "Student_Number": number}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_selection, data=json_data)
        return r.content

    def __add(self, course_number, num, start):
        data = {"Course_Number": course_number, "Student_Number": start + num}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_selection, data=json_data)
        print(r.content)

    def add_task(self, course_number, num, start):
        for i in range(num):
            threading.Thread(target=self.__add(course_number, i, start), args=(course_number, i, start,)).start()

    def delete(self, selection_number):
        data = {"Selection_Number": selection_number}
        json_data = json.dumps(data)
        r = requests.delete(local_host + url_api_selection, data=json_data)
        return r.content

    def query_all(self):
        r = requests.get(local_host + url_api_selection)
        return r.content

    def query_by_number(self, selection_number):
        r = requests.get((local_host + url_api_selection + str(selection_number)))
        return r.content

    def filter_by_student(self, student_number):
        r = requests.get((local_host + url_api_selection + 'query?studentid=' + str(student_number)))
        return r.content

    def filter_by_instructor(self, instructor_number):
        r = requests.get((local_host + url_api_selection + 'query?instructorid=' + str(instructor_number)))
        return r.content

    def filter_by_student_and_instructor(self, student_number, instructor_number):
        r = requests.get((local_host + url_api_selection +
                          'query?studentid=' + str(student_number) +
                          '&instructorid=' + str(instructor_number)))
        return r.content


class UnitTestHelper:
    def __init__(self, number):
        self.__number = number
        self.__student = Student()
        self.__instructor = Instructor()
        self.__course = Course()
        self.__selection = Selection()

    def add_test(self):
        is_pass_status_code("Add student", self.__student.add_test(self.__number))
        is_pass_status_code("Add instructor", self.__instructor.add_test(self.__number))
        is_pass_status_code("Add course", self.__course.add_test(self.__number))
        is_pass_status_code("Add selection", self.__selection.add_test(self.__number))

    def query_test(self):
        # student
        is_pass_content("Query student all", self.__student.query_all())
        is_pass_content("Query student by number", self.__student.query_by_number(self.__number))
        is_pass_content("Query student's selection", self.__student.query_selection(self.__number))
        # instructor
        is_pass_content("Query instructor all", self.__instructor.query_all())
        is_pass_content("Query instructor by number", self.__instructor.query_by_number(self.__number))
        is_pass_content("Query instructor's course", self.__instructor.query_course(self.__number))
        # course
        is_pass_content("Query course all", self.__course.query_all())
        is_pass_content("Query course by number", self.__course.query_by_number(str(self.__number).zfill(5)))
        # selection
        is_pass_content("Query selection all", self.__selection.query_all())
        is_pass_content("Query selection by number", self.__selection.query_by_number(self.__number))
        is_pass_content("Filter selection by student", self.__selection.filter_by_student(self.__number))
        is_pass_content("Filter selection by instructor", self.__selection.filter_by_instructor(self.__number))
        is_pass_content("Filter selection by student and instructor",
                        self.__selection.filter_by_student_and_instructor(self.__number, self.__number))

    def delete_test(self):
        # selection
        is_pass_status_code("Delete selection", self.__selection.delete(self.__number))
        # course
        is_pass_status_code("Delete course", self.__course.delete(str(self.__number).zfill(5)))

    def test_all(self):
        print('------------------------------')
        self.add_test()
        print('------------------------------')
        self.query_test()
        print('------------------------------')
        self.delete_test()
        print('------------------------------')


def is_pass_status_code(test_type, status_code):
    if status_code.decode('utf-8') == '200':
        print(bcolors.OKGREEN + test_type + ": Pass" + bcolors.ENDC)
    else:
        print(bcolors.FAIL + test_type + ": Failed" + bcolors.ENDC)


def is_pass_content(test_type, content):
    if content.decode('utf-8') != '{}' and content.decode('utf-8') != '[]':
        print(bcolors.OKGREEN + test_type + ": Pass" + bcolors.ENDC)
    else:
        print(bcolors.FAIL + test_type + ": Failed" + bcolors.ENDC)


if __name__ == '__main__':
    # Student
    # student = Student()
    # ADD
    #student.add_student_task(50)
    # Query
    # student.query_all()
    # student.query_by_number(1)
    # student.query_selection(1)

    # Instructor
    # instructor = Instructor()
    # ADD
    # instructor.add_instructor_task()
    # Query
    # instructor.query_all()
    # instructor.query_by_number(1)
    # instructor.query_course(1)

    # Course
    # course = Course()
    # ADD
    # course.add_course_task(5)
    # Delete
    #course.delete_course("jUfsN")
    # Query
    # course.query_all()
    # course.query_by_number("rhleQ")

    # Selection
    #selection = Selection()
    # ADD
    #selection.add_task('01003', 50, 146)
    # Delete
    # selection.delete_selection(465)
    # Query
    # selection.query_all()
    # selection.query_by_number(407)
    # selection.filter_by_student(1)
    # selection.filter_by_instructor(1)
    # selection.filter_by_student_and_instructor(2, 1)

    unit_test_helper = UnitTestHelper(1003)
    #unit_test_helper.add_test()
    # unit_test_helper.query_test()
    # unit_test_helper.delete_test()
    unit_test_helper.test_all()

