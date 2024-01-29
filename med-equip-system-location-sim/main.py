import json, pika, uuid, threading, os 
from coordinate import *

coordinates = []
connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost'))
channel = connection.channel()
channel.queue_declare(queue='coordinates')

def send_message(m):
    channel.basic_publish(exchange='', routing_key='coordinates', body=m)
    print(" [x] Sent:\n " + m)


def read_coordinates():
    if not coordinates:
        print("No coordinates found")
    for c in coordinates:
        print(json.dumps(c.to_dict(), indent=1))

def main():
    
    for i in range(1, 6):
        new_coordinate = Coordinate(i, 45.0 + i * 0.1, 19.0 + i * 0.1)
        send_message(json.dumps(new_coordinate.to_dict(),indent=1))
        coordinates.append(new_coordinate)

    read_coordinates()
    '''while True:

        choice = input("Enter your choice: ")
        if choice == 'read all coordinates - 1':
            read_coordinates()                      #delete
        elif choice == '0':
            print("Exiting...")
            break
        else:
            print("Invalid choice, try again")
        input("Press enter to continue...")
    '''

    connection.close()

if __name__ == '__main__':
    main()