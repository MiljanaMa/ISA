import json
import pika
import threading
from coordinate import *
import time

coordinates = []
connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost'))

channel = connection.channel()

channel.queue_delete(queue='startsending')
channel.queue_delete(queue='coordinates')
channel.queue_declare(queue='coordinates', durable=True)

#channel.queue_purge(queue='startsending')
#channel.queue_purge(queue='coordinates')

channel.queue_declare(queue='coordinates', durable=True)
channel.queue_declare(queue='startsending', durable=False)

stop_sending = False

def send_message(m):
    channel.basic_publish(exchange='', routing_key='coordinates', body=m)
    print(" [x] Sent:\n " + m)

def start_sending_coordinates():
    while not stop_sending:
        method_frame, header_frame, body = channel.basic_get(queue='startsending')
        if method_frame:
            main()

def main():
    coordinates_data = [
        (45.240025, 19.825688),
        (45.241906, 19.825255),
        (45.244497, 19.825201),
        (45.246657, 19.825092),
        (45.247354, 19.823847)
    ]

    for i, coord_data in enumerate(coordinates_data, start=1):
        new_coordinate = Coordinate(coord_data[0], coord_data[1])
        send_message(json.dumps(new_coordinate.to_dict(), indent=1))
        coordinates.append(new_coordinate)
        time.sleep(10) 

if __name__ == '__main__':
    try:
        while True:
            method_frame, header_frame, body = channel.basic_get(queue='startsending')
            if method_frame:
                main()
    except KeyboardInterrupt:
        stop_sending = True
        connection.close()

