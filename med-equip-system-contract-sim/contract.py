from enum import Enum 

class Status(Enum): 
    INACTIVE = 1 
    ACTIVE = 2 
    CANCELLED = 3
    INVALID = 4
    FINISH = 5 

status_mapping = {
    "start": Status.ACTIVE, 
    "invalid": Status.INVALID,
    "cancel": Status.CANCELLED,
    "finish": Status.INACTIVE
}

class Contract:
    def __init__(self, id, date, time, equipment, total, company, status, hospital):
        self.id = id
        self.date = date
        self.time = time
        self.equipment = equipment
        self.total = total
        self.company = company
        self.status = status 
        self.hospital = hospital 

    def to_dict(self):
        return {"id": self.id,
                "date": self.date,
                "time": self.time,
                "equipmentName": self.equipment,
                "total": self.total,
                "companyName": self.company, 
                "status": self.status.name, 
                "hospital": self.hospital}

