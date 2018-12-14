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


class Student:

    def __add_studnet(self, student_number):
        data = {"Student_Name": "Student" + str(student_number), "Student_Gender": gender[student_number % 3]}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_student, data=json_data)
        print(r.content)

    def add_student_task(self, num):
        for i in range(num):
            threading.Thread(target=self.__add_studnet(i), args=(i,)).start()

    def query_all(self):
        r = requests.get(local_host + url_api_student)
        print(r.content)

    def query_by_number(self, student_number):
        r = requests.get((local_host + url_api_student + str(student_number)))
        print(r.content)

    def query_selection(self, student_number):
        r = requests.get((local_host + url_api_student + str(student_number) + '/selection'))
        print(r.content)


class Instructor:

    def __add_instructor(self, instructor_number):
        data = {"Instructor_Name": "Instructor" + str(instructor_number),
                "Instructor_Office": ''.join(random.choice(string.ascii_letters) for x in range(4))}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_instructor, data=json_data)
        print(r.content)

    def add_instructor_task(self, num):
        for i in range(num):
            threading.Thread(target=self.__add_instructor(i), args=(i,)).start()

    def query_all(self):
        r = requests.get(local_host + url_api_instructor)
        print(r.content)

    def query_by_number(self, instructor_number):
        r = requests.get((local_host + url_api_instructor + str(instructor_number)))
        print(r.content)

    def query_course(self, instructor_number):
        r = requests.get((local_host + url_api_instructor + str(instructor_number) + '/course'))
        print(r.content)


class Course:

    def __add_course(self, course_number):
        data = {"Course_Number": ''.join(random.choice(string.ascii_letters) for x in range(5)),
                "Course_Title": "Sleep", "Course_Size": 10, "Course_Weekday": course_number % 5 + 1,
                "Instructor_Number": course_number + 1, "Course_Classtime": class_time[course_number % 3]}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_course, data=json_data)
        print(r.content)

    def add_course_task(self, num):
        for i in range(num):
            threading.Thread(target=self.__add_course(i), args=(i,)).start()

    def delete_course(self, course_number):
        data = {"Course_Number": course_number}
        json_data = json.dumps(data)
        r = requests.delete(local_host + url_api_course, data=json_data)
        print(r.content)

    def query_all(self):
        r = requests.get(local_host + url_api_course)
        print(r.content)

    def query_by_number(self, course_number):
        r = requests.get((local_host + url_api_course + str(course_number)))
        print(r.content)


class Selection:

    def __add_selection(self, course_number, num, start):
        data = {"Course_Number": course_number, "Student_Number": start + num}
        json_data = json.dumps(data)
        r = requests.post(local_host + url_api_selection, data=json_data)
        print(r.content)

    def add_selection_task(self, course_number, num, start):
        for i in range(num):
            threading.Thread(target=self.__add_selection(course_number, i, start), args=(course_number, i, start)).start()

    def delete_selection(self, selection_number):
        data = {"Selection_Number": selection_number}
        json_data = json.dumps(data)
        r = requests.delete(local_host + url_api_selection, data=json_data)
        print(r.content)

    def query_all(self):
        r = requests.get(local_host + url_api_selection)
        print(r.content)

    def query_by_number(self, selection_number):
        r = requests.get((local_host + url_api_selection + str(selection_number)))
        print(r.content)

    def filter_by_student(self, student_number):
        r = requests.get((local_host + url_api_selection + 'query?studentid=' + str(student_number)))
        print(r.content)

    def filter_by_instructor(self, instructor_number):
        r = requests.get((local_host + url_api_selection + 'query?instructorid=' + str(instructor_number)))
        print(r.content)

    def filter_by_student_and_instructor(self, student_number, instructor_number):
        r = requests.get((local_host + url_api_selection +
                          'query?studentid=' + str(student_number) +
                          '&instructorid=' + str(instructor_number)))
        print(r.content)


if __name__ == '__main__':
    # Student
    student = Student()
    # ADD
    #student.add_student_task(50)
    # Query
    # student.query_all()
    # student.query_by_number(1)
    # student.query_selection(1)

    # Instructor
    instructor = Instructor()
    # ADD
    # instructor.add_instructor_task()
    # Query
    # instructor.query_all()
    # instructor.query_by_number(1)
    # instructor.query_course(1)

    # Course
    course = Course()
    # ADD
    # course.add_course_task(5)
    # Delete
    #course.delete_course("jUfsN")
    # Query
    # course.query_all()
    # course.query_by_number("rhleQ")

    # Selection
    selection = Selection()
    # ADD
    selection.add_selection_task('uspde', 50, 146)
    # Delete
    # selection.delete_selection(465)
    # Query
    # selection.query_all()
    # selection.query_by_number(407)
    # selection.filter_by_student(1)
    # selection.filter_by_instructor(1)
    # selection.filter_by_student_and_instructor(2, 1)
